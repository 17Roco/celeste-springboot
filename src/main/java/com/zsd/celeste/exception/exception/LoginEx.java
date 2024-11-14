package com.zsd.celeste.exception.exception;

public class LoginEx extends RuntimeException {
    public LoginEx() {
        super("登录失败，请检查用户名或密码");
    }

    public LoginEx(String message) {
        super(message);
    }
}

