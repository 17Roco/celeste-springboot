package com.zsd.celeste.service.inter;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

public interface selectWrapperInterface<T> {
    void setWrapper(QueryWrapper<T> wrapper);
}
