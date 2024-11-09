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
        Map<String, HashMap<String, Object>> collect = service.getCache().entrySet().stream().collect(Collectors.toMap(
                Map.Entry::getKey,
                e -> {
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("uid", e.getValue().getUser().getUid());
                    map.put("username", e.getValue().getUser().getUsername());
                    map.put("password", e.getValue().getUser().getPassword());
                    return map;
                }
        ));

        return Result.ok(collect);
    }

    @PutMapping("/upload")
    Result updateToken(@RequestParam("file") MultipartFile file) {
        return Result.ok(fileResourceService.saveResource(file, ResourceNameSpace.IMAGE_DEV));
    }
}
