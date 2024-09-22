package com.zsd.celeste.controller;

import com.zsd.celeste.entity.DO.LoginForm;
import com.zsd.celeste.entity.PO.User;
import com.zsd.celeste.service.UserService;
import com.zsd.celeste.util.AutUtil;
import com.zsd.celeste.util.result.DataResult;
import com.zsd.celeste.util.result.Result;
import com.zsd.celeste.util.result.StreamResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

/**
 * (User)表控制层
 *
 * @author zsd
 * @since 2024-06-07 00:43:08
 */
@RestController
public class UserController {
    
    @Autowired
    private UserService service;
    @Autowired
    private AutUtil autUtil;


    @PostMapping("/login")
    Result login(@RequestBody LoginForm form) {
        if (!StringUtils.hasText(form.getUsername()) || !StringUtils.hasText(form.getPassword()))
            throw new RuntimeException("密码或用户名不能为空");
        return StreamResult.create("token",service.login(form.getUsername(), form.getPassword()));
    }

    @PostMapping("/register")
    Result register(@RequestBody LoginForm form) {
        if (!StringUtils.hasText(form.getUsername()) || !StringUtils.hasText(form.getPassword()))
            throw new RuntimeException("密码或用户名不能为空");
        return StreamResult.create("token",service.register(form.getUsername(), form.getPassword()));
    }

    @PreAuthorize("@autUtil.needLogin()")
    @PostMapping("/logout")
    Result logout() {
        return Result.ok();
    }
}

