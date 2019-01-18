package com.clo.scs.service;

import com.clo.scs.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author XuHong
 * @date 2019年01月17日 17:19
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class ApiServiceTest {
    @Autowired
    private ApiService apiService;

    @Test
    public void testHello() {
        System.err.println(apiService.hello("123"));
    }
}
