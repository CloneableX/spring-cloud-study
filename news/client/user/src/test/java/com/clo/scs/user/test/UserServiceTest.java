package com.clo.scs.user.test;

import com.clo.scs.common.domain.result.SingleResult;
import com.clo.scs.user.request.LoginRequest;
import com.clo.scs.user.response.TokenResponse;
import com.clo.scs.user.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    public void loginTest() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setMobile("13000000000");
        loginRequest.setPassword("12345678");
        SingleResult<TokenResponse> result = userService.login(loginRequest);
        System.err.println(result);
    }
}
