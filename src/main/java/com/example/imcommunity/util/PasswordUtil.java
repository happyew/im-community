package com.example.imcommunity.util;

import com.example.imcommunity.entity.User;
import org.apache.shiro.crypto.hash.Md5Hash;

public class PasswordUtil {
    public static void setMd5Hash(User user, String password) {
        user.setPassword(new Md5Hash(password, user.getSalt(), 1024).toHex());
    }

    public static String toMd5Hash(String password, String salt) {
        return new Md5Hash(password, salt, 1024).toHex();
    }
}
