package com.zsd.celeste.entity.PO.base;

/**
* pojo接口，所有pojo都需要实现该接口
* 该接口定义了pojo的id属性，用于标识pojo的唯一性，并提供get和set方法
* */
public interface Pojo {
    Integer Id();
    void Id(Integer id);
}
