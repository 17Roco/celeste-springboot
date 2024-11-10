package com.zsd.celeste.util.base.service;

import com.zsd.celeste.entity.PO.UserPojo;
import com.zsd.celeste.exception.exception.pojo.NotPojoOwnerEx;
import com.zsd.celeste.exception.exception.pojo.PojoSaveFailEx;
import com.zsd.celeste.util.AutUtil;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Function;

public interface BasePojoService<T extends UserPojo> extends BaseService<T> {

    /**
     * 获取实体类
     * */
    default T needBySelf(Serializable id){
        T t = needById(id);
        if (!Objects.equals(t.getUid(), AutUtil.self().getUid()))
            throw new NotPojoOwnerEx();
        return t;
    }

    /**
     * 删除实体类
     * */
    default boolean removeBySelf(Serializable id){
        T t = needBySelf(id);
        return removeById(t);
    }

    /**
     * 保存实体类
     * */
    default T saveBySelf(T t){
        t.setUid(AutUtil.uid());
        if (!save(t))
            throw new PojoSaveFailEx();
        return needBySelf(t.Id());
    }

    /**
     * 更新实体类
     * */
    default boolean updateBySelf(Serializable id, Function<T,T> updateByForm){
        T t = needBySelf(id);
        return updateById(updateByForm.apply(t));
    }
}
