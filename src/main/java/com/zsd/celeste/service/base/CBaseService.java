package com.zsd.celeste.service.base;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zsd.celeste.service.inter.selectWrapperInterface;

import java.util.List;

public interface CBaseService<T> extends IService<T> {
    default int getPageSize(){return 20;}
    default IPage<T> page(int index){
        return select(wrapper -> {},index);
    }
    IPage<T> select(selectWrapperInterface<T> wrapperInterface, int index);

    default List<T> select(selectWrapperInterface<T> wrapperInterface){
        return select(wrapperInterface,1).getRecords();
    }
    default IPage<T> selectEq(String column,Object val,int index) {
        return select(wrapper -> wrapper.eq(column,val),index);
    }
}
