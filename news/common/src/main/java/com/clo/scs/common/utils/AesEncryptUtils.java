package com.clo.scs.common.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;

/**
 * @author XuHong
 * @date 2019年01月31日 16:17
 */
public class AesEncryptUtils {
    private static final String ALGORITHM_STR = "AES/ECB/PKCS5Padding";
////    private static final String ALGORITHM_STR = "AES/CBC/NoPadding"; //TODO 如果此 Cipher 为 Cipher 块，未请求任何填充（只针对加密模式），并且由此 Cipher 处理的数据总输入长度不是块大小的倍数时抛出IllegalBlockSizeException

    public static String base64Encrypt(byte[] bytes) {
        return Base64.encodeBase64String(bytes);
    }

    public static byte[] base64Decrypt(String base64Code) throws UnsupportedEncodingException {
        return Base64.decodeBase64(base64Code.getBytes("utf-8"));
    }

    public static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        Cipher cipher = Cipher.getInstance(ALGORITHM_STR);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));
        return cipher.doFinal(content.getBytes("utf-8"));
    }

    public static String aesEncrypt(String content, String encryptKey) throws Exception {
        return base64Encrypt(aesEncryptToBytes(content, encryptKey));
    }

    public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        Cipher cipher = Cipher.getInstance(ALGORITHM_STR);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));
        byte[] decryptBytes = cipher.doFinal(encryptBytes);
        return new String(decryptBytes);
    }

    public static String aesDecrypt(String encryptStr, String decryptKey) throws Exception {
        return aesDecryptByBytes(base64Decrypt(encryptStr), decryptKey);
    }

    public static void main(String[] args) {
        String key = "d7b86f6a234abcda";
        String content = "hello";
        System.err.println("加密前:" + content);

        try {
            String encryptStr = aesEncrypt(content, key);
            System.err.println(encryptStr.length() + ":加密后:" + encryptStr);

            String decryptStr = aesDecrypt(encryptStr, key);
            System.err.println(decryptStr.length() + ":解密后:" + decryptStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
