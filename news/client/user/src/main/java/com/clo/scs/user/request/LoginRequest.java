package com.clo.scs.user.request;

import com.clo.scs.common.domain.request.BaseRequest;
import org.hibernate.validator.constraints.NotEmpty;

public class LoginRequest extends BaseRequest {
    @NotEmpty
    private String mobile;
    @NotEmpty
    private String password;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
