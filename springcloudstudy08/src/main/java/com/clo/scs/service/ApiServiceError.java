package com.clo.scs.service;

import org.springframework.stereotype.Component;

/**
 * @author XuHong
 * @date 2019年01月18日 9:36
 */
@Component
public class ApiServiceError implements ApiService {
    @Override
    public String hello(String name) {
        return "服务发生故障";
    }
}
