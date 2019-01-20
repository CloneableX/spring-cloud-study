package com.clo.scs.handler;

import com.clo.scs.entity.RedissonConnector;
import com.clo.scs.exception.UnableToLockException;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
public class RedisLocker implements DistributeLocker {
    private static final String LOCK_PREFIX = "lock:";
    private static final int LOCK_TIME = 100;

    @Autowired
    private RedissonConnector connector;

    @Override
    public <T> T lock(String resourceName, AfterLockWorker<T> worker) throws UnableToLockException, Exception {
        return lock(resourceName, worker, LOCK_TIME);
    }

    @Override
    public <T> T lock(String resourceName, AfterLockWorker<T> worker, int lockTime) throws UnableToLockException, Exception {
        //获取redis连接
        RedissonClient client = connector.getConnector();
        //获取lock
        RLock lock = client.getLock(LOCK_PREFIX + resourceName);
        //测试lock
        boolean success = lock.tryLock(LOCK_TIME, lockTime, TimeUnit.MILLISECONDS);
        if(success) {
            try {
                return worker.invokeAfterLock();
            } finally {
                lock.unlock();
            }
        }
        throw new UnableToLockException();
    }
}
