package com.example.imcommunity.util;

import kotlin.text.Charsets;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.springframework.util.Base64Utils;

import java.util.Arrays;

/**
 * 盐工具类
 */
public class SaltUtil {
//    private static final char[] CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ~%^*()_-+0123456789".toCharArray();

    public static String getSalt() {
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < 8; i++) {
//            sb.append(CHARS[RandomUtil.randomInt(CHARS.length)]);
//        }
        return new SecureRandomNumberGenerator().nextBytes().toHex();
    }
}
