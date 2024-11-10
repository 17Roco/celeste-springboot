package com.zsd.celeste.exception.exception.pojo;

public class NotExistEx extends RuntimeException {
    public NotExistEx() {
        super("对象不存在");
    }
    public NotExistEx(String message) {
        super(message+"不存在");
    }
}
