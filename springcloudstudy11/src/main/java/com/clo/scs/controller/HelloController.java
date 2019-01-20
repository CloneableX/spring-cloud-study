package com.clo.scs.controller;

import com.clo.scs.entity.Lock;
import com.clo.scs.handler.AfterLockWorker;
import com.clo.scs.handler.DistributeLockHandler;
import com.clo.scs.handler.DistributeLocker;
import com.clo.scs.handler.ZooDistributeLocker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @Autowired
    private DistributeLockHandler distributeLockHandler;
    @Autowired
    private DistributeLocker distributeLocker;

    @GetMapping("/hello")
    public String hello() {
        Lock lock = new Lock("xh", "hello");
        if(distributeLockHandler.tryLock(lock)) {
            try {
                System.err.println("执行方法");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            distributeLockHandler.releaseLock(lock);
        }
        return "hello";
    }

    @GetMapping("/rlock")
    public String rlock() throws Exception {
        distributeLocker.lock("test", new AfterLockWorker<Object>() {
            @Override
            public Object invokeAfterLock() throws Exception {
                System.err.println("执行方法！");
                Thread.sleep(5000);
                return null;
            }
        });

        return "hello rlock";
    }

    @GetMapping("/zoo")
    public String zoo() throws InterruptedException {
        ZooDistributeLocker locker = new ZooDistributeLocker("lock", "localhost:2181");
        locker.lock();

        if(locker != null) {
            System.err.println("执行方法！");
            Thread.sleep(5000);
            locker.unlock();
        }

        return "hello zoo";
    }
}
