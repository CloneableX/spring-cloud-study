package com.clo.scs.common.domain.response;

/**
 * @author XuHong
 * @date 2019年02月01日 14:21
 */
public class KeyResponse extends BaseResponse {
    private String key;

    public static class Builder {
        private String key;

        public KeyResponse build() {
            return new KeyResponse(this);
        }
        public Builder setKey(String key) {
            this.key = key;
            return this;
        }
    }

    public KeyResponse(Builder builder) {
        this.key = builder.key;
    }

    public static Builder options() {
        return new Builder();
    }

    public String getKey() {
        return key;
    }
}
