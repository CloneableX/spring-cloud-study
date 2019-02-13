package com.clo.scs.common.controller;

import com.clo.scs.common.domain.request.KeyRequest;
import com.clo.scs.common.domain.request.TestRequest;
import com.clo.scs.common.domain.response.KeyResponse;
import com.clo.scs.common.domain.response.RSAResponse;
import com.clo.scs.common.domain.result.SingleResult;
import com.clo.scs.common.interfaces.EncryptOpenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

@RestController
@RequestMapping(value = "open/encrypt")
public class EncryptOpenController {
    @Autowired
    private EncryptOpenService encryptOpenService;

    @RequestMapping(value = "getRSA", method = RequestMethod.POST)
    public SingleResult<RSAResponse> getRSA() {
        return encryptOpenService.getRSA();
    }

    @RequestMapping(value = "getKey", method = RequestMethod.POST)
    public SingleResult<KeyResponse> getKey(@Valid @RequestBody KeyRequest keyRequest) throws InvalidKeySpecException, NoSuchAlgorithmException {
        return encryptOpenService.getKey(keyRequest);
    }

    @RequestMapping(value = "testValid", method = RequestMethod.POST)
    @ResponseBody
    public String testValid(@Valid @RequestBody TestRequest testRequest, BindingResult result) {
        validate(result);
        return "请求成功";
    }

    private void validate(BindingResult result) {
        if(result.hasFieldErrors()) {
            List<FieldError> fieldErrorList = result.getFieldErrors();
            fieldErrorList.stream().forEach(item -> Assert.isTrue(false, item.getDefaultMessage()));
        }
    }
}
