package com.clo.scs.user.response;

import com.clo.scs.common.domain.response.BaseResponse;

public class TokenResponse extends BaseResponse {
    public String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
