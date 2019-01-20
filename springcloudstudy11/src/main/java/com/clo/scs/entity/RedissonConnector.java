package com.clo.scs.entity;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RedissonConnector {
    private RedissonClient connector;

    @PostConstruct
    public void init() {
        connector = Redisson.create();
    }

    public RedissonClient getConnector() {
        return connector;
    }
}
