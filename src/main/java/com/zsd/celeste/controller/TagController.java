package com.zsd.celeste.controller;

import com.zsd.celeste.pojo.Tag;
import com.zsd.celeste.service.TagService;
import com.zsd.celeste.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/tag")
public class TagController {
    
    @Autowired
    private TagService service;

    /**
     * 获取全部标签信息
    * */
    @GetMapping("/list")
    Result getList(){
        return Result.success(service.list());
    }


    /**
     * 根据标签名创建标签
     * */
    @PostMapping("/{tagName}")
    @PreAuthorize("@autUtil.root()")
    Result addTag(@PathVariable String tagName){
        Tag tag = service.getTagByTitle(tagName);
        if(!Objects.isNull(tag)){
            throw new RuntimeException("标签已存在");
        }
        tag = new Tag();
        tag.setTitle(tagName);
        return Result.judge(service.save(tag));
    }

}
