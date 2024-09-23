package com.zsd.celeste.util.base.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface BaseService<T> extends IService<T> {

    default int getSize(){
        return 20;
    }

    default IPage<T> page(int index){
        IPage<T> p = Page.of(index,getSize());
        return page(p);
    }

}
