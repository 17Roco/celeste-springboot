package com.zsd.celeste.service.base;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsd.celeste.service.inter.selectWrapperInterface;

public class CBaseServiceImpl<M extends BaseMapper<T>,T> extends ServiceImpl<M,T> implements CBaseService<T>{
    @Override
    public IPage<T> select(selectWrapperInterface<T> wrapperInterface, int index) {
        IPage<T> p = Page.of(index, getPageSize());

        QueryWrapper<T> wrapper = new QueryWrapper<>();
        wrapperInterface.setWrapper(wrapper);

        return page(p,wrapper);
    }
}
