package com.zsd.celeste.controller;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zsd.celeste.entity.PO.Tag;
import com.zsd.celeste.service.TagService;
import com.zsd.celeste.util.base.controller.BaseAllListController;
import com.zsd.celeste.util.base.controller.rest.RESTController;
import com.zsd.celeste.util.base.service.BaseService;
import com.zsd.celeste.util.result.DataResult;
import com.zsd.celeste.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/tag")
public class TagController {
    
    @Autowired
    private TagService service;


    @GetMapping("/list")
    Result getList() {
        return DataResult.ok(service.list());
    }

    @PostMapping("/{title}")
    public Result add(@PathVariable String title) {
        Tag tag = new Tag(null, title, "", 0);
        return Result.judge(service.save(tag));
    }
}
