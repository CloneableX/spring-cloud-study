package com.clo.scs.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "hello")
public interface ApiService {
    @RequestMapping(value = "hello", method = RequestMethod.GET)
    public String hello(@RequestParam("name") String name);
}
