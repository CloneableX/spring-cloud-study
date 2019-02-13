package com.clo.scs.user.controller;

import com.clo.scs.common.controller.BaseController;
import com.clo.scs.common.domain.result.SingleResult;
import com.clo.scs.user.request.LoginRequest;
import com.clo.scs.user.response.TokenResponse;
import com.clo.scs.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public SingleResult<TokenResponse> login(@Valid @RequestBody LoginRequest loginRequest, BindingResult bindingResult) {
        validate(bindingResult);
        return userService.login(loginRequest);
    }
}
