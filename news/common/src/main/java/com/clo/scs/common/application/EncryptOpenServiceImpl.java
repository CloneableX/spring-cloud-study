package com.clo.scs.common.application;

import com.clo.scs.common.domain.request.KeyRequest;
import com.clo.scs.common.domain.response.KeyResponse;
import com.clo.scs.common.domain.response.RSAResponse;
import com.clo.scs.common.domain.result.SingleResult;
import com.clo.scs.common.interfaces.EncryptOpenService;
import com.clo.scs.common.utils.RSAUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Service
public class EncryptOpenServiceImpl implements EncryptOpenService {
    @Value("${rsa.publicKey}")
    private String publicKey;

    @Value("${rsa.privateKey}")
    private String privateKey;

    @Value("${api.encrypt.key}")
    private String key;

    @Override
    public SingleResult<RSAResponse> getRSA() {
        RSAResponse response = RSAResponse.options()
                                          .setPublicKey(publicKey)
                                          .build();
        return SingleResult.buildSuccess(response);
    }

    @Override
    public SingleResult<KeyResponse> getKey(KeyRequest keyRequest) throws InvalidKeySpecException, NoSuchAlgorithmException {
        String clientPublicKey = RSAUtils.privateDecrypt(keyRequest.getClientEncryptPublicKey(), RSAUtils.getPrivateKey(privateKey));
        String encryptKey = RSAUtils.publicEncrypt(key, RSAUtils.getPublicKey(clientPublicKey));
        KeyResponse response = KeyResponse.options()
                                          .setKey(encryptKey)
                                          .build();
        return SingleResult.buildSuccess(response);
    }
}
