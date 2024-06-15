package com.zsd.celeste.service.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsd.celeste.mapper.LinkMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class CBaseServiceImpl<M extends BaseMapper<T>,T> extends ServiceImpl<M,T> implements CBaseService<T>{
    @Autowired
    protected LinkMapper linkMapper;
    @Override
    public LinkMapper getLinkMapper() {
        return linkMapper;
    }
}
