package com.zsd.celeste.util;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class PojoUtil {

    static public  <T>T copy(T target,Object source){
        BeanUtils.copyProperties(source,target);
        return target;
    }

    static public  <T>T copy(Object source,Class<T> clazz) {
        if (source == null)
            return null;
        try {
            T t = clazz.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source,t);
            return t;
        } catch (Exception e) {
            throw new RuntimeException(e+"copy fail");
        }
    }
    static public  <T>List<T> copy(Collection<?> source, Class<T> clazz)  {
        return source.stream().map(s -> copy(s, clazz)).toList();
    }
}
