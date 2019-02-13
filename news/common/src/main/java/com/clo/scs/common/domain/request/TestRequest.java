package com.clo.scs.common.domain.request;

import javax.validation.constraints.NotNull;

public class TestRequest extends BaseRequest {
    @NotNull(message = "名字不能为空")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
