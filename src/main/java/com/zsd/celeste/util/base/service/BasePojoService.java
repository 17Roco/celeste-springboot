package com.zsd.celeste.util.base.service;

import com.zsd.celeste.entity.PO.UserPojo;
import com.zsd.celeste.util.AutUtil;

import java.io.Serializable;
import java.util.Objects;

public interface BasePojoService<T extends UserPojo> extends BaseService<T> {

    default T needBySelf(Serializable id){
        T t = needById(id);
        if (!Objects.equals(t.getUid(), AutUtil.self().getUid()))
            throw new RuntimeException("无法访问该资源");
        return t;
    }
}
