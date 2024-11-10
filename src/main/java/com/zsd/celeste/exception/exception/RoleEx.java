package com.zsd.celeste.exception.exception;

public class RoleEx extends RuntimeException {
    public RoleEx() {
        super("权限不足");
    }
}
