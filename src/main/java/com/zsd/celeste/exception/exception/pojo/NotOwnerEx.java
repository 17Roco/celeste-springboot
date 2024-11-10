package com.zsd.celeste.exception.exception.pojo;

public class NotOwnerEx extends RuntimeException {
    public NotOwnerEx() {
        super("无法访问");
    }
}
