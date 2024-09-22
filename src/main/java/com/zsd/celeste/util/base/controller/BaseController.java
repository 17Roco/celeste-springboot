package com.zsd.celeste.util.base.controller;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zsd.celeste.util.base.service.BaseService;

public interface BaseController<T> {

    BaseService<T> getService();
}
