package com.clo.scs.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MessageDigestUtils {
    public static String encrypt(String text, String algorithm) {
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            byte[] buff = md.digest(text.getBytes("UTF-8"));
            return ByteUtils.byte2HexString(buff);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        System.err.println(encrypt("admin", Algorithm.SHA1));
    }
}
