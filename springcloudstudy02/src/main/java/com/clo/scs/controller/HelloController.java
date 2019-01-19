package com.clo.scs.controller;

import com.clo.scs.entity.RemoteConn;
import com.clo.scs.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class HelloController {
    @Value("${userDefined.testValue.ip}")
    String ip;

    @Value("${userDefined.testValue.port}")
    String port;

    @Autowired
    private RemoteConn remoteConn;

    @RequestMapping(value = "/hello")
    public String hello() {
        return "I'm from ip:" + ip + " port:" + port;
    }

    @RequestMapping(value = "/remote/conn")
    public String remoteConn() {
        return remoteConn.toString();
    }

    @RequestMapping(value = "/show/exception")
    public String showException() throws Exception {
        throw new RuntimeException("方法错误");
    }

    @GetMapping("/login")
    public void login(@Valid User user, BindingResult bindingResult) {
        if(bindingResult.hasFieldErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            fieldErrors.stream().forEach(item -> Assert.isTrue(false, item.getDefaultMessage()));
        }
    }
}
