package com.zsd.celeste.util.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zsd.celeste.entity.PO.Article;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public interface BaseService<T> extends IService<T> {

    default int getSize(){
        return 20;
    }

    default IPage<T> page(int index){
        IPage<T> p = Page.of(index,getSize());
        return page(p);
    }

    default T needById(Serializable id){
        T entity = getById(id);
        if (Objects.isNull(entity))
            throw new RuntimeException("资源不存在");
        return entity;
    }

}
