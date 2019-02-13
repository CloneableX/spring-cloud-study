package com.clo.scs.common.domain.request;

import javax.validation.constraints.NotNull;

/**
 * @author XuHong
 * @date 2019年02月01日 14:37
 */
public class KeyRequest extends BaseRequest {
    @NotNull
    private String clientEncryptPublicKey;

    public String getClientEncryptPublicKey() {
        return clientEncryptPublicKey;
    }

    public void setClientEncryptPublicKey(String clientEncryptPublicKey) {
        this.clientEncryptPublicKey = clientEncryptPublicKey;
    }
}
