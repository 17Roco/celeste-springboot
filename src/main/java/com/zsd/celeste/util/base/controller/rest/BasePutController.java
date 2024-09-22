package com.zsd.celeste.util.base.controller.rest;

import com.zsd.celeste.util.base.controller.BaseController;
import com.zsd.celeste.util.result.Result;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface BasePutController<T> extends BaseController<T> {
    @PutMapping("/")
    default Result update(@RequestBody T pojo) {
        boolean b = this.getService().updateById(pojo);
        return Result.judge(b);
    }
}
