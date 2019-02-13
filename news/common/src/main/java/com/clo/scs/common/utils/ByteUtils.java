package com.clo.scs.common.utils;

public class ByteUtils {
    public static String byte2HexString(byte[] b) {
        int len = 0;
        if(null == b || (len = b.length) == 0){
            throw new NullPointerException("byte[] is empty.");
        }
        StringBuilder builder = new StringBuilder(len);
        String hexString = null;
        for (int i = 0; i < len; i++) {
            hexString = Integer.toHexString(b[i] & 0xFF);
            if(hexString.length() < 2){
                builder.append('0').append(hexString);
            }else {
                builder.append(hexString);
            }
        }
        return builder.toString();
    }
}
