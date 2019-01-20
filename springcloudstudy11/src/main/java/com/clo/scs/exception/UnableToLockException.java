package com.clo.scs.exception;

public class UnableToLockException extends RuntimeException {
    public UnableToLockException() {}

    public UnableToLockException(String message) {
        super(message);
    }

    public UnableToLockException(String message, Throwable cause) {
        super(message, cause);
    }
}
