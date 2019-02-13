package com.clo.scs.common.interfaces;

import com.clo.scs.common.domain.request.KeyRequest;
import com.clo.scs.common.domain.response.KeyResponse;
import com.clo.scs.common.domain.response.RSAResponse;
import com.clo.scs.common.domain.result.SingleResult;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface EncryptOpenService {
    SingleResult<RSAResponse> getRSA();

    SingleResult<KeyResponse> getKey(KeyRequest keyRequest) throws InvalidKeySpecException, NoSuchAlgorithmException;
}
