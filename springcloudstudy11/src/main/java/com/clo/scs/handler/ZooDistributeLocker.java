package com.clo.scs.handler;

import com.clo.scs.exception.LockException;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class ZooDistributeLocker implements Lock, Watcher {
    private ZooKeeper zooKeeper;
    private String root = "/locks";
    private String lockName;
    private String myZnode;
    private String waitZnode;
    private CountDownLatch latch;
    private CountDownLatch connectSignal = new CountDownLatch(1);

    private static final int SESSION_TIMEOUT = 30000;

    public ZooDistributeLocker() {}

    public ZooDistributeLocker(String lockName, String config) {
        this.lockName = lockName;
        try {
            zooKeeper = new ZooKeeper(config, SESSION_TIMEOUT, this);
            connectSignal.await();
            Stat stat = zooKeeper.exists(root, false);
            if(stat == null) {
                myZnode = zooKeeper.create(root, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if(watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            connectSignal.countDown();
            return;
        }

        if(latch != null) {
            latch.countDown();
        }
    }

    @Override
    public void lock() {
        try {
            if(tryLock()) {
                System.err.println("Thread:" + Thread.currentThread().getId());
                return;
            } else {
                //等待锁
                waitForLock(waitZnode, SESSION_TIMEOUT);
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean waitForLock(String subZnode, long waitTime) throws KeeperException, InterruptedException {
        Stat stat = zooKeeper.exists(root + "/" + subZnode, true);
        if(stat != null) {
            System.err.println("Thread:" + Thread.currentThread().getId() + " is waiting for " + root + "/" + subZnode);
            latch = new CountDownLatch(1);
            latch.await(waitTime, TimeUnit.MILLISECONDS);
            latch = null;
        }

        return true;
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        this.lock();
    }

    @Override
    public boolean tryLock() {
        //创建锁
        String lockSuffix = "_lock_";
        if(lockName.contains(lockSuffix)) {
            throw new LockException("lockName can not contains \\u000B");
        }

        try {
            //获取此锁名字的最小版本
            myZnode = zooKeeper.create(root + "/" + lockName + lockSuffix, new byte[0], ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
            System.err.println(myZnode + " is created");

            List<String> zChildrenNodes = zooKeeper.getChildren(root, false);
            List<String> lockZnodes = new ArrayList<String>();
            for(String zChildNode : zChildrenNodes) {
                String nodeName = zChildNode.split(lockSuffix)[0];
                if(nodeName.equals(lockName)) {
                    lockZnodes.add(zChildNode);
                }
            }
            Collections.sort(lockZnodes);

            if(myZnode.equals(root + "/" + lockZnodes.get(0))) {
                System.err.println(myZnode + "==" + lockZnodes.get(0));
                return true;
            }

            //如果最小版本不为此锁查找此锁的子锁
            String subZnode = myZnode.substring(myZnode.lastIndexOf("/") + 1);
            waitZnode = lockZnodes.get(Collections.binarySearch(lockZnodes, subZnode));
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        try {
            if(tryLock()) {
                return true;
            } else {
                //等待锁
                waitForLock(waitZnode, time);
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void unlock() {
        try {
            System.err.println("unlock:" + myZnode);
            zooKeeper.delete(myZnode, -1);
            myZnode = null;
            zooKeeper.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
