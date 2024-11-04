package com.zsd.celeste.util.base.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zsd.celeste.exception.exception.pojo.PojoNotExistEx;

import java.io.Serializable;
import java.util.Objects;

public interface BaseService<T> extends IService<T> {

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
     * 当资源不存在时触发异常 PojoNotExistEx
     * */
    default T need(T entity){
        if (Objects.isNull(entity)) throw new PojoNotExistEx(getResourceMsg());
        return entity;
    }
    /**
     * 获取资源，不存在则报错 、 获取资源同时鉴权 PojoNotExistEx
     * */
    default T needById(Serializable id){
        return need(getById(id));
    }

    /**
     * 分页查询、分页带条件查询
     * */
    default IPage<T> page(int index){
        return page(index,null);
    }
    default IPage<T> page(int index, Wrapper<T> wrapper){
        return page(index,getSize(),wrapper);
    }
    default IPage<T> page(int index,int size, Wrapper<T> wrapper){
        IPage<T> p = Page.of(index,size);
        return page(p, wrapper);
    }
    /**
     * 分页
     * */

}
