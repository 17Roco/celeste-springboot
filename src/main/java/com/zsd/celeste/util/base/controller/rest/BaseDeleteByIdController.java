package com.zsd.celeste.util.base.controller.rest;

import com.zsd.celeste.util.result.Result;
import com.zsd.celeste.util.base.controller.BaseController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface BaseDeleteByIdController<T> extends BaseController<T> {

    @DeleteMapping("/{id}")
    default Result delete(@PathVariable Integer id) {
        boolean b = this.getService().removeById(id);
        return Result.judge(b);
    }
}
