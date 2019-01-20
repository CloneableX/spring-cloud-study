package com.clo.scs.exception;

public class LockException extends RuntimeException {
    public LockException() {}

    public LockException(String message) {
        super(message);
    }

    public LockException(String message, Throwable cause) {
        super(message, cause);
    }
}
