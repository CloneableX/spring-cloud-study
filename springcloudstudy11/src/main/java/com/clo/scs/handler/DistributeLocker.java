package com.clo.scs.handler;

import com.clo.scs.exception.UnableToLockException;

public interface DistributeLocker {
    <T> T lock(String resourceName, AfterLockWorker<T> worker) throws UnableToLockException, Exception;
    <T> T lock(String resourceName, AfterLockWorker<T> worker, int lockTime) throws UnableToLockException, Exception;
}
