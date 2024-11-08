package com.zsd.celeste.util;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Component
public class HashUtil {

    private String getNow(){
        return new Date().toString();
    }

    private String to16baseString(byte[] hashBytes){
        // 将字节数组转换成 16 进制字符串
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public String hash(String str) {
        // 创建一个MD5消息摘要
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        // 生成字符串
        String input = (getNow() + str);
        // 生成 MD5 摘要
        byte[] hashBytes = md.digest(input.getBytes());
        // 转换成 16 进制字符串
        return to16baseString(hashBytes);
    }
}
