package com.zsd.celeste.util.base.controller.rest;

public interface RESTController<T>
        extends
        BaseDeleteByIdController<T>,
        BaseGetByIdController<T>,
        BasePostController<T>,
        BasePutController<T>
{}
