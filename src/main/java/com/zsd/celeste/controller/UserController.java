package com.zsd.celeste.controller;

import com.zsd.celeste.entity.DO.LoginForm;
import com.zsd.celeste.entity.DO.UpdatePassword;
import com.zsd.celeste.service.UserService;
import com.zsd.celeste.util.AutUtil;
import com.zsd.celeste.util.result.DataResult;
import com.zsd.celeste.util.result.Result;
import com.zsd.celeste.util.result.StreamResult;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class UserController {

    @Autowired
    private UserService service;


    @PostMapping("/login")
    Result login(@RequestBody LoginForm form) {
        return StreamResult.create("token",service.login(form.getUsername(), form.getPassword()));
    }

    @PostMapping("/register")
    Result register(@RequestBody LoginForm form) {
        return Result.judge(service.register(form.getUsername(), form.getPassword()));
    }

    @PreAuthorize("@autUtil.needLogin()")
    @PostMapping("/logout")
    Result logout() {
        return Result.judge(service.logout(AutUtil.self().getToken()));
    }

    @PreAuthorize("@autUtil.needLogin()")
    @PostMapping("/changePassword")
    Result changePassword(@RequestBody UpdatePassword update){
        return Result.judge(service.updatePassword(AutUtil.self().getUsername(), update.getOldPassword(), update.getNewPassword()));
    }

    @PreAuthorize("@autUtil.needLogin()")
    @GetMapping("/self")
    Result self() {
        return DataResult.ok(service.needInfoById(AutUtil.self().getUid()));
    }
}
