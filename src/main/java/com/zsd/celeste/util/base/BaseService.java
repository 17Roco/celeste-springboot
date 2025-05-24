package com.zsd.celeste.util.base;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zsd.celeste.entity.PO.base.Pojo;
import com.zsd.celeste.exception.exception.ResourceSaveFailEx;
import com.zsd.celeste.exception.exception.pojo.NotExistEx;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.Function;


/**
 * 提供分页查询、分页带条件查询、资源不存在时触发异常
 * getSize() 20
 *  need
 *  needById
 *  page(index)
*/
public interface BaseService<T extends Pojo> extends IService<T> {

    /**
     * 默认分页大小
     * */
    default int getSize(){
        return 20;
    }

    /**
     * 当资源不存在时触发异常 PojoNotExistEx
     * */
    default T need(T entity){
        if (Objects.isNull(entity))
            throw new NotExistEx();
        return entity;
    }
    /**
     * 获取资源，不存在则报错 、 获取资源同时鉴权 PojoNotExistEx
     * */
    default T needById(Serializable id){
        return need(getById(id));
    }

    /**
     * 删除资源，不存在则报错 、 删除资源同时鉴权 PojoNotExistEx
     * */
    default boolean deleteOne(Serializable id) {
        return deleteOne(id,t->{});
    }
    default boolean deleteOne(Serializable id,EditPojoInterface<T> before) {
        // 获取资源
        T t = needById(id);
        // 调用before方法
        before.edit(t);
        // 删除资源
        return removeById(id);
    }



    /**
     * 修改资源
     * */
    default T change(Serializable id, EditPojoInterface<T> edit,EditPojoInterface<T> before){
        // 获取资源
        T t = needById(id);
        // 调用before方法
        before.edit(t);
        // 修改资源
        edit.edit(t);
        // 更新资源
        if (!updateById(t))
            throw new ResourceSaveFailEx("");
        return t;
    }
    default T change(Serializable id, EditPojoInterface<T> edit){
        return change(id,edit,t->{});
    }

    /**
     * 创建资源
     * */
    default T create(T entity){
        if (!save(entity)) {
            throw new ResourceSaveFailEx("");
        }
        return entity;
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

}
