package com.clo.scs.common.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.List;

public class MD5Utils {
    public static String md5(String text, String key) {
        return DigestUtils.md5Hex(text + key);
    }

    public static void main(String[] args) {
        String encryptStr = md5("timestamp=1549371776923&token=123456", "d7b86f6a234abcda");
        System.err.println(encryptStr);
//        List<String> strList = new ArrayList<String>();
//        strList.add("token");
//        strList.add("timestamp");
//        strList.stream().sorted().forEach(name -> {
//            System.err.println(name);
//        });
    }
}
