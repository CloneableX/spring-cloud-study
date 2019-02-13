package com.clo.scs.common.domain.result;

public enum Code {
    Error(0, "请求失败"),
    Success(1, "请求成功");

    private int status;
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    Code(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
