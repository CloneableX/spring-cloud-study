package com.clo.scs.common.controller;

import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public abstract class BaseController {
    protected void validate(BindingResult result) {
        if(result.hasFieldErrors()) {
            List<FieldError> fieldErrorList = result.getFieldErrors();
            fieldErrorList.stream().forEach(item -> Assert.isTrue(false, item.getDefaultMessage()));
        }
    }
}
