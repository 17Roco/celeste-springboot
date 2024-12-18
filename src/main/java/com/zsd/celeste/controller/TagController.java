package com.zsd.celeste.controller;

import com.zsd.celeste.entity.PO.Tag;
import com.zsd.celeste.service.TagService;
import com.zsd.celeste.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/tag")
public class TagController {
    
    @Autowired
    private TagService service;

    /**
     * 获取标签列表
     * */
    @GetMapping("/list")
    Result getList() {
        return Result.ok(service.list());
    }

    /**
     * 添加标签
     * */
    @PreAuthorize("@autUtil.isAdmin()")
    @PostMapping("/{title}")
    public Result add(@PathVariable String title) {
        Tag tag = new Tag(null, title, "", 0);
        return Result.judge(service.save(tag));
    }
}
