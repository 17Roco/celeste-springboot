package com.zsd.celeste.util.base.controller.rest;

import com.zsd.celeste.util.base.controller.BaseController;
import com.zsd.celeste.util.result.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface BasePostController<T> extends BaseController<T> {
    @PostMapping("/")
    default Result Post(@RequestBody T pojo) {
        boolean save = this.getService().save(pojo);
        return Result.judge(save);
    }
}