package com.zsd.celeste.util.base.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zsd.celeste.entity.PO.UserPojo;
import com.zsd.celeste.exception.exception.ResourceNotfoundEx;
import com.zsd.celeste.util.AutUtil;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Function;

public interface BaseService<T extends UserPojo> extends IService<T> {

    /**
     * 默认分页大小
     * */
    default int getSize(){
        return 20;
    }
    /**
     * 默认资源不存在异常信息
     * */
    default String getResourceMsg(){
        return "资源不存在";
    }
    /**
     * 当资源不存在时触发异常
     * */
    default T need(T entity){
        if (Objects.isNull(entity)) throw new ResourceNotfoundEx(getResourceMsg());
        return entity;
    }



    /**
     * 分页查询、分页带条件查询
     * */
    default IPage<T> page(int index){
        return page(index,null);
    }
    default IPage<T> page(int index, QueryWrapper<T> wrapper){
        return page(index,getSize(),wrapper);
    }
    default IPage<T> page(int index,int size, QueryWrapper<T> wrapper){
        IPage<T> p = Page.of(index,size);
        return page(p, wrapper);
    }


    /**
     * 获取资源，不存在则报错 、 获取资源同时鉴权
     * */
    default T needById(Serializable id){
        return need(getById(id));
    }
    default T needBySelf(Serializable id){
        T t = needById(id);
        if (!Objects.equals(t.getUid(), AutUtil.self().getUid()))
            throw new RuntimeException("无法访问该资源");// todo
        return t;
    }
}
