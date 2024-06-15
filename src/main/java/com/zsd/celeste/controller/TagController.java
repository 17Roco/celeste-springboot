package com.zsd.celeste.controller;

import com.zsd.celeste.pojo.Tag;
import com.zsd.celeste.service.TagService;
import com.zsd.celeste.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/tag")
public class TagController {
    
    @Autowired
    private TagService service;
    
    @GetMapping("/list")
    Result getList(){
        return Result.success(service.list());
    }
    
    @PutMapping("/{aid}/{tagName}")
    Result addTag(@PathVariable Integer aid,@PathVariable String tagName){
        Tag tag = service.getTagByTitle(tagName);
        if(Objects.isNull(tag)){
            tag = new Tag();
            tag.setTitle(tagName);
            if (!service.save(tag)) {
                throw new RuntimeException("标签创建失败");
            }
        }

        return null;
    }
}
