package com.zsd.celeste.util.base.controller.rest;


import com.zsd.celeste.util.result.Result;
import com.zsd.celeste.util.result.DataResult;
import com.zsd.celeste.util.base.controller.BaseController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface BaseGetByIdController<T> extends BaseController<T> {
    @GetMapping("/{id}")
    default Result getById(@PathVariable Integer id) {
        return DataResult.ok(getService().getById(id));
    }
}