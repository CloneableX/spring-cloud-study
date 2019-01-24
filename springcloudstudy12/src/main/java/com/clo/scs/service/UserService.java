package com.clo.scs.service;

import com.clo.scs.mapper.test01.UserMapper1;
import com.clo.scs.mapper.test02.UserMapper2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class UserService {
    @Resource
    private UserMapper1 userMapper1;
    @Resource
    private UserMapper2 userMapper2;

    @Transactional
    public void addUser(String name, int age) {
        userMapper1.addUser(name, age);
        String a = null;
        a.equals("");
        userMapper2.addUser(name, age);
    }
}
