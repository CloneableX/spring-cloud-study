package com.clo.scs.common.interfaces;

import com.clo.scs.common.utils.Algorithm;
import com.clo.scs.common.utils.MessageDigestUtils;
import org.apache.commons.codec.digest.DigestUtils;

public abstract class BaseService {
    protected String encryptPassword(String password) {
        return MessageDigestUtils.encrypt(password, Algorithm.SHA1);
    }

    protected String getToken(String mobile, String password) {
        return MessageDigestUtils.encrypt(mobile + password, Algorithm.SHA1);
    }
}
