package com.zsd.celeste.controller.sys;

import com.zsd.celeste.entity.form.LoginForm;
import com.zsd.celeste.service.common.AuthService;
import com.zsd.celeste.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private AuthService service;

    /**
     * 登录
     * @param form 登录表单
     * */
    @PostMapping("/login")
    Result login(@RequestBody LoginForm form) {
        return Result.map("token",service.login(form.getUsername(), form.getPassword()));
    }

    /**
     * 注册
     * @param form 注册表单
     * */
    @PostMapping("/register")
    Result register(@RequestBody LoginForm form) {
        return Result.judge(service.register(form.getUsername(), form.getPassword()));
    }

    /**
     * 退出
     * */
    @PostMapping("/logout")
    Result logout() {
        // todo get token
        return Result.judge(service.logout(null));
    }

}
