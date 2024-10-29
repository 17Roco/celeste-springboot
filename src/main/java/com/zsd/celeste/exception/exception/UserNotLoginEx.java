package com.zsd.celeste.exception.exception;

public class UserNotLoginEx extends RuntimeException {
    public UserNotLoginEx() {
        super("需要登录");
    }
}
