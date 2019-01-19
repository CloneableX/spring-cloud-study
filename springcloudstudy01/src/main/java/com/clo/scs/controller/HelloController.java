package com.clo.scs.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class HelloController
        extends SpringBootServletInitializer
{
    public static void main(String[] args) {
        SpringApplication.run(HelloController.class, args);
    }

    @RequestMapping("hello")
    String hello() {
        return "Hello World";
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(HelloController.class);
    }
}
