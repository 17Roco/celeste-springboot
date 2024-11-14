package com.zsd.celeste.exception.exception;

public class TokenEx extends RuntimeException {
    public TokenEx() {
        super("用户登录信息已失效，请重新登录");
    }
}
