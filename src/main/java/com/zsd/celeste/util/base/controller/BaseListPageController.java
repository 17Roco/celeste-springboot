package com.zsd.celeste.util.base.controller;

import com.zsd.celeste.util.result.DataResult;
import com.zsd.celeste.util.result.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface BaseListPageController<T> extends BaseController<T> {
    @GetMapping({"/list"})
    default Result getPage() {
        return this.getPage(1);
    }

    @GetMapping({"/list/{index}"})
    default Result getPage(@PathVariable Integer index) {
        return DataResult.ok(this.getService().page(index));
    }
}