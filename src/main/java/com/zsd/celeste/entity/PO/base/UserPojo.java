package com.zsd.celeste.entity.PO.base;


import java.util.Objects;

/**
 * 继承了Pojo接口的UserPojo接口
 * 用于定义用户相关的pojo，提供uid属性，用于标识用户
 * */
public interface UserPojo extends Pojo{
    Integer getUid();
    void setUid(Integer uid);

    default boolean isOwner(Integer uid){
        return Objects.equals(getUid(), uid);
    }
}
