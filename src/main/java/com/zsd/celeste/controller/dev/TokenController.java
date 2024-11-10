package com.zsd.celeste.controller.dev;

import com.zsd.celeste.enums.ResourceNameSpace;
import com.zsd.celeste.service.FileResourceService;
import com.zsd.celeste.service.TokenService;
import com.zsd.celeste.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
