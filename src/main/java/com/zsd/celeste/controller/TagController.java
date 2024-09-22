package com.zsd.celeste.controller;

import com.zsd.celeste.entity.PO.Tag;
import com.zsd.celeste.service.TagService;
import com.zsd.celeste.util.base.controller.BaseAllListController;
import com.zsd.celeste.util.base.controller.rest.RESTController;
import com.zsd.celeste.util.base.service.BaseService;
import com.zsd.celeste.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/tag")
public class TagController implements BaseAllListController<Tag> {
    
    @Autowired
    private TagService service;


    @Override
    public BaseService<Tag> getService() {
        return service;
    }

    @PostMapping("/{title}")
    public Result add(@PathVariable String title) {
        Tag tag = new Tag(null, title, "", 0);
        return Result.judge(service.save(tag));
    }
}
