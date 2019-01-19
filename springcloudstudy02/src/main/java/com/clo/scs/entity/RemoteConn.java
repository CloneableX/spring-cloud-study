package com.clo.scs.entity;

public class RemoteConn {
    private String ip;
    private String port;

    public static class Builder {
        private String ip;
        private String port;

        public RemoteConn build() {
            return new RemoteConn(this);
        }

        public Builder setIp(String ip) {
            this.ip = ip;
            return this;
        }

        public Builder setPort(String port) {
            this.port = port;
            return this;
        }
    }

    public RemoteConn(Builder builder) {
        this.ip = builder.ip;
        this.port = builder.port;
    }

    public static Builder options() {
        return new RemoteConn.Builder();
    }

    public String getIp() {
        return ip;
    }

    public String getPort() {
        return port;
    }

    @Override
    public String toString() {
        return "This connect ip:" + ip + " port:" + port;
    }
}
