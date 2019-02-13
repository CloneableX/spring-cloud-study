package com.clo.scs.common.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * @author XuHong
 * @date 2019年02月01日 10:13
 */
public class RSAUtils {
    private static final String RSA_ALGORITHM = "RSA";
    public static final String CHART_SET = "UTF-8";

    /**
     *
     * <B>方法名称：</B>createKeys<BR>
     * <B>概要说明：</B>生成密钥对<BR>
     *
     * @param keySize 密钥长度
     * @return java.util.Map<java.lang.String,java.lang.String> 密钥对map
     *
     * @author XuHong
     * @date 2019/2/1 10:26
     */
    public static Map<String, String> createKeys(int keySize) {
        // 创建密钥对生成器
        KeyPairGenerator keyPairGenerator;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("No such algorithm-->[" + RSA_ALGORITHM + "]");
        }
        // 初始密钥长度
        keyPairGenerator.initialize(keySize);
        // 生成密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        // 生成公钥并Base64Encode
        PublicKey publicKey = keyPair.getPublic();
        String publicKeyStr = Base64.encodeBase64String(publicKey.getEncoded());
        // 生成私钥并Base64Encode
        PrivateKey privateKey = keyPair.getPrivate();
        String privateKeyStr = Base64.encodeBase64String(privateKey.getEncoded());

        Map<String, String> keysMap = new HashMap<>();
        keysMap.put("publicKey", publicKeyStr);
        keysMap.put("privateKey", privateKeyStr);
        return keysMap;
    }

    /**
     *
     * <B>方法名称：</B>getPrivateKey<BR>
     * <B>概要说明：</B><获取私钥对象BR>
     *
     * @param privateKey 私钥字符串
     * @return java.security.interfaces.RSAPrivateKey 私钥对象
     *
     * @author XuHong
     * @date 2019/2/1 11:06
     */
    public static RSAPrivateKey getPrivateKey(String privateKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // 通过PKCS#8编码的key指令获取私钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
        return (RSAPrivateKey) keyFactory.generatePrivate(pkcs8EncodedKeySpec);
    }

    /**
     *
     * <B>方法名称：</B>getPublicKey<BR>
     * <B>概要说明：</B>获取公钥对象<BR>
     *
     * @param publicKey 公钥字符串
     * @return java.security.interfaces.RSAPublicKey 公钥对象
     *
     * @author XuHong
     * @date 2019/2/1 11:07
     */
    public static RSAPublicKey getPublicKey(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // 通过x509编码的key指令获取公钥对象
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey));
        return (RSAPublicKey) keyFactory.generatePublic(x509EncodedKeySpec);
    }

    /**
     *
     * <B>方法名称：</B>publicEncrypt<BR>
     * <B>概要说明：</B>公钥加密<BR>
     *
     * @param content 需要加密内容
     * @param publicKey 公钥对象
     * @return java.lang.String 加密后内容
     *
     * @author XuHong
     * @date 2019/2/1 11:08
     */
    public static String publicEncrypt(String content, RSAPublicKey publicKey) {
        try {
            // 获取Cipher实例
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            // 初始化Cipher
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            // 获取Cipher处理后的byte数组
            byte[] encryptBuff = rsaSplitCodec(cipher, Cipher.ENCRYPT_MODE, content.getBytes(CHART_SET), publicKey.getModulus().bitLength());
            // Base64 encode后返回
            return Base64.encodeBase64String(encryptBuff);
        } catch (Exception e) {
            throw new RuntimeException("加密字符串[" + content + "]时遇到异常", e);
        }
    }

    /**
     *
     * <B>方法名称：</B>privateDecrypt<BR>
     * <B>概要说明：</B>私钥解密<BR>
     *
     * @param encryptStr 加密内容
     * @param privateKey 私钥对象
     * @return java.lang.String 解密内容
     *
     * @author XuHong
     * @date 2019/2/1 14:03
     */
    public static String privateDecrypt(String encryptStr, RSAPrivateKey privateKey) {
        try {
            // 获取Cipher实例
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            // 初始化Cipher
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            // 获取Cipher处理后的byte数组
            byte[] decryptBuff = rsaSplitCodec(cipher, Cipher.DECRYPT_MODE, Base64.decodeBase64(encryptStr), privateKey.getModulus().bitLength());
            return new String(decryptBuff, CHART_SET);
        } catch (Exception e) {
            throw new RuntimeException("解密字符串[" + encryptStr + "]时遇到异常", e);
        }
    }

    /**
     *
     * <B>方法名称：</B>rsaSplitCodec<BR>
     * <B>概要说明：</B>Cipher操作<BR>
     *
     * @param cipher
     * @param opmode cipher模式
     * @param datas cipher操作的数据内容
     * @param keySize 密钥长度
     * @return byte[] 经cipher操作的结果内容
     *
     * @author XuHong
     * @date 2019/2/1 14:04
     */
    public static byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] datas, int keySize) {
        // 根据Cipher类型和密钥长度获取maxBlock值
        int maxBlock = 0;
        if(opmode == Cipher.DECRYPT_MODE) {
            maxBlock = keySize / 8;
        } else {
            maxBlock = keySize / 8 - 11;
        }
        // 根据maxBlock值将要处理内容写入outputStream

        byte[] buff;
        int offset = 0;
        int i = 0;
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();) {
            while (datas.length > offset) {
                if(datas.length - offset > maxBlock) {
                    buff = cipher.doFinal(datas, offset, maxBlock);
                } else {
                    buff = cipher.doFinal(datas, offset, datas.length - offset);
                }

                outputStream.write(buff, 0, buff.length);
                i++;
                offset = i * maxBlock;
            }

            // 通过outputStream转换为byte数组
            byte[] outputBuff = outputStream.toByteArray();
            return outputBuff;
        } catch (Exception e) {
            throw new RuntimeException("加解密阈值为[" + maxBlock + "]的数据时发生异常");
        } finally {

        }
    }

    public static void main(String[] args) throws Exception {
        // 创建密钥对
        Map<String, String> keysMap = createKeys(1024);
//        String publicKeyStr = keysMap.get("publicKey");
        String publicKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCcZlkHaSN0fw3CWGgzcuPeOKPdNKHdc2nR6KLXazhhzFhe78NqMrhsyNTf365saFS2lADK3CzASzH4T0bT+GnJ77joDOP+0SqubHKwAIv850lT0QxS+deuUHg2+uHYhdhIw5NCmZ0SkNalw8igP1yS+2TEIYan3lakPBvZISqRswIDAQAB";
//        String privateKeyStr = keysMap.get("privateKey");
        String privateKeyStr = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIGbU0JtKvQsPYWeRpcc4J1skRZ5P4jgbiGMnl+8RbHUyS02/3G2S2sx3bIRpcFi5M9DoQqVb93v8qQtDAcKhmPICjvGmplLzeIGOjEbKfW4Pssnhbky31vvfrvcwhoTmHvOv8QAnZodp00dpU1eZqMTCKYXofkYd7i+p4V+Y8M1AgMBAAECgYAo/3RP7+6FfLUdXlrgFE9tHNmsbUBO0QelbzDuAcVeInxc5Cfb1Zl32DdTy8dOLDVK5w67JVhUsUFk86b5Vzf5QaNSlpn8xxUj8bnBFeUlVUKLfnY4/7Ar8s1NygxvZhmRbOToGTmsW54CPdH+LioFCo+e8s8sVWTACZU1p98uIQJBAPe+HHTXDQ93YynAkWMZ81mbe3PeNUC5MU9Z5aXnp34iO6c7b+JQcHUUi95l5v1rrh8+HhbAjJEmJMffKmnxDokCQQCF7TUwlpgQmZXheuLDoV/0g9zCqVZbEtIWBWPJikyeEcAxHrx80iNhvKGDXCXH9An/5aFZlFeEOv/TTUk/VkRNAkAcms/QUdBJO+CwPnLK/YESlJhBfaOqcHeYEOoqKMA3GR7IJV26xMznR9MKf7uXASreop54xAy26a+PgF32U4X5AkBXsRpC1lh7hhU9rtkMf330/OJwE7EXsRsekCjmrke8uKK/hwCkOnQwavLWsF+MrZ5ekF016ovjaT5ZfSGY90OBAkEA0pmz/lmvwGuLvKGWq62hRxhE4TpPWindURr62LkK65D+gPlj65A6I/cOnQAnSidyhkUrzQCKd6H2RpaPEqgmBg==";

//        System.err.println(publicKeyStr);
//        System.err.println(privateKeyStr);
//        String content = "站在大明门前守卫的禁卫军，事先没有接到\n" +
//                "有关的命令，但看到大批盛装的官员来临，也就\n" +
//                "以为确系举行大典，因而未加询问。进大明门即\n" +
//                "为皇城。文武百官看到端门午门之前气氛平静，\n" +
//                "城楼上下也无朝会的迹象，既无几案，站队点名\n" +
//                "的御史和御前侍卫“大汉将军”也不见踪影，不免\n" +
//                "心中揣测，互相询问：所谓午朝是否讹传？";
//        String content = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCBm1NCbSr0LD2FnkaXHOCdbJEWeT+I4G4hjJ5fvEWx1MktNv9xtktrMd2yEaXBYuTPQ6EKlW/d7/KkLQwHCoZjyAo7xpqZS83iBjoxGyn1uD7LJ4W5Mt9b73673MIaE5h7zr/EAJ2aHadNHaVNXmajEwimF6H5GHe4vqeFfmPDNQIDAQAB";
//        System.err.println(content.length() + ":加密前:" + content);
        // 使用公钥加密
//        String encryptStr = publicEncrypt(content, getPublicKey(publicKeyStr));
        String encryptStr = "VvPFnBSZWvxxsVAr8xYNN6+uoFIuoOCN4wRCkIZF2wR5CS+6ECGBNArMSfNn869nMPliA5Ds0Pt9uSWi2ccKd0za+ar22+CjiH4CruBMb4UBazd+45kzA2STU6euqkDAfHdd5NOqsDxgu1cq0ttyxCDAVI3Re0gZX9l385aYnvc=";
        System.err.println(encryptStr.length() + ":加密后:" + encryptStr);
//        // 使用私钥解密
        String decryptStr = privateDecrypt(encryptStr, getPrivateKey(privateKeyStr));
        System.err.println(decryptStr.length() + ":解密后:" + decryptStr);
    }
}
