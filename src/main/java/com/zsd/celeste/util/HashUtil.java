package com.zsd.celeste.util;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class HashUtil {

    private String getNow(){
        return new Date().toString();
    }

    public String hash(String str) {
        int hashCode = (getNow() + str).hashCode();
        return String.valueOf(hashCode);
    }
}
