package com.zsd.celeste.controller;

import com.zsd.celeste.util.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @RequestMapping("/1")
    Result t1(){
        return Result.success(1);
    }
}
