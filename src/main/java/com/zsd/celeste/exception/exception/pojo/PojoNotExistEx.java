package com.zsd.celeste.exception.exception.pojo;

public class PojoNotExistEx extends RuntimeException {
    public PojoNotExistEx(String resourceMsg) {
        super("对象不存在"+resourceMsg);
    }
}
