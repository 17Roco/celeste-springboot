package com.zsd.celeste.exception.exception;

public class ResourceSaveFailEx extends RuntimeException{
    public ResourceSaveFailEx(String msg) {
        super("资源保存失败:"+msg);
    }
}
