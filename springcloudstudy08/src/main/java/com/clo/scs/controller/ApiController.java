package com.clo.scs.controller;

import com.clo.scs.service.ApiService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author XuHong
 * @date 2019年01月18日 9:41
 */
@RestController
public class ApiController {
    @Resource
    private ApiService apiService;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(String name) {
        return apiService.hello(name);
    }
}
