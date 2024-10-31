package com.zsd.celeste.controller;

import com.zsd.celeste.entity.DO.LoginForm;
import com.zsd.celeste.service.UserService;
import com.zsd.celeste.util.AutUtil;
import com.zsd.celeste.util.result.DataResult;
import com.zsd.celeste.util.result.Result;
import com.zsd.celeste.util.result.StreamResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService service;

    /**
     * 登录
     * */
    @PostMapping("/login")
    Result login(@RequestBody LoginForm form) {
        return StreamResult.create("token",service.login(form.getUsername(), form.getPassword()));
    }

    /**
     * 注册
     * */
    @PostMapping("/register")
    Result register(@RequestBody LoginForm form) {
        return Result.judge(service.register(form.getUsername(), form.getPassword()));
    }

    /**
     * 退出
     * */
    @PreAuthorize("@autUtil.needLogin()")
    @PostMapping("/logout")
    Result logout() {
        return Result.judge(service.logout(AutUtil.self().getToken()));
    }



    /**
     * 获取当前用户信息
     * */
    @PreAuthorize("@autUtil.needLogin()")
    @GetMapping("/self")
    Result self() {
        return DataResult.ok(service.needInfoById(AutUtil.self().getUid()));
    }

}
