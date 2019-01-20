package com.clo.scs.handler;

import com.clo.scs.entity.Lock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

@Component
public class DistributeLockHandler {
    private static final long TIME_OUT = 20 * 1000L;
    private static final long TIME_INTERVAL = 30L;
    private static final long LOCK_EXPIRE = 30 * 1000L;

    @Autowired
    private StringRedisTemplate template;

    public boolean tryLock(Lock lock) {
        return getLock(lock, TIME_OUT, TIME_INTERVAL, LOCK_EXPIRE);
    }

    public boolean getLock(Lock lock, long timeout, long timeInterval, long lockExpireTime) {
        //Lock是否存在
        if(StringUtils.isEmpty(lock.getName()) || StringUtils.isEmpty(lock.getValue())) {return false;}

        long startTime = System.currentTimeMillis();
        try {
            do {
                //不存在就新增
                if(!template.hasKey(lock.getName())) {
                    ValueOperations<String, String> ops = template.opsForValue();
                    ops.set(lock.getName(), lock.getValue(), lockExpireTime, TimeUnit.MILLISECONDS);
                    return true;
                } else {
                    System.err.println("lock is exist");
                }

                //存在就定时获取,超时就失败
                if(System.currentTimeMillis() - startTime > timeout) {
                    return false;
                }
                Thread.sleep(timeInterval);
            } while (template.hasKey(lock.getName()));
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }

        return false;
    }

    public void releaseLock(Lock lock) {
        if(!StringUtils.isEmpty(lock.getName())) {
            template.delete(lock.getName());
        }
    }
}
