package com.zsd.celeste.util;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

public class PojoUtil {

    static public  <T>T copy(T target,Object source){
        BeanUtils.copyProperties(source,target);
        return target;
    }
}
