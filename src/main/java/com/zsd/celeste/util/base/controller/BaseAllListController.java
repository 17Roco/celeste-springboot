package com.zsd.celeste.util.base.controller;

import com.zsd.celeste.util.result.DataResult;
import com.zsd.celeste.util.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface BaseAllListController<T> extends BaseController<T> {
    @GetMapping("/list")
    default Result getList() {
        return DataResult.ok(this.getService().list());
    }
}