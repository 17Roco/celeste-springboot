package com.zsd.celeste.controller.dev;

import com.zsd.celeste.service.TokenService;
import com.zsd.celeste.util.result.Result;
import com.zsd.celeste.util.result.StreamResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/dev")
public class TokenController {
    @Autowired
    TokenService service;
    @GetMapping("/token")
    Result getTable(){
        return StreamResult
                .create("table",service.getCache().entrySet().stream().collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> {
                            HashMap<String, Object> map = new HashMap<>();
                            map.put("uid",e.getValue().getUser().getUid());
                            map.put("username",e.getValue().getUser().getUsername());
                            map.put("password",e.getValue().getUser().getPassword());
                            return map;
                        }
                )))
                .put("reTable",service.getReCache());
    }
}
