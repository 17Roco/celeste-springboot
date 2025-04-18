package com.zsd.celeste.controller.dev;

import com.zsd.celeste.enums.ResourceNameSpace;
import com.zsd.celeste.service.common.FileResourceService;
import com.zsd.celeste.service.common.TokenService;
import com.zsd.celeste.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/dev")
public class TokenController {
    @Autowired
    TokenService service;
    @Autowired
    FileResourceService fileResourceService;

    @GetMapping("/token")
    Result getTable(){
        return Result.ok(service.getMap());
    }



    @PutMapping("/upload")
    Result updateToken(@RequestParam("file") MultipartFile file) {
        return Result.ok(fileResourceService.saveResource(file, ResourceNameSpace.IMAGE_DEV));
    }
}
