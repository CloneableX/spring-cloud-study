package com.clo.scs.common.domain.response;

/**
 * @author XuHong
 * @date 2019年02月01日 14:21
 */
public class RSAResponse extends BaseResponse {
    private String publicKey;

    public static class Builder {
        private String publicKey;

        public RSAResponse build() {
            return new RSAResponse(this);
        }
        public Builder setPublicKey(String publicKey) {
            this.publicKey = publicKey;
            return this;
        }
    }

    public RSAResponse(Builder builder) {
        this.publicKey = builder.publicKey;
    }

    public static Builder options() {
        return new Builder();
    }

    public String getPublicKey() {
        return publicKey;
    }
}
