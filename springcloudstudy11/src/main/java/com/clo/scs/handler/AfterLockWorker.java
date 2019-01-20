package com.clo.scs.handler;

public interface AfterLockWorker<T> {
    T invokeAfterLock() throws Exception;
}
