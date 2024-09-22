package com.zsd.celeste.util.base.service;

import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface BaseService<T> extends IService<T> {

    default List<T> page(int index){
        System.out.println("-----------------------------------");
        return null;
    }

}
