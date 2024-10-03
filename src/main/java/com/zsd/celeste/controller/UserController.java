package com.zsd.celeste.controller;

import com.zsd.celeste.entity.DO.LoginForm;
import com.zsd.celeste.entity.DO.UpdatePasswordDO;
import com.zsd.celeste.service.UserService;
import com.zsd.celeste.util.AutUtil;
import com.zsd.celeste.util.result.Result;
import com.zsd.celeste.util.result.StreamResult;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService service;
    @Autowired
    private AutUtil autUtil;


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
        return Result.judge(service.logout(autUtil.getToken()));
    }

    @PreAuthorize("@autUtil.needLogin()")
    @PostMapping("/pw")
    Result changePassword(@RequestBody UpdatePasswordDO update){
        return Result.judge(service.updatePassword(autUtil.getLoginUser().getUsername(), update.getOldPassword(), update.getNewPassword()));
    }




    @PreAuthorize("@autUtil.needLogin()")
    @PostMapping({"/follow/{uid}","/unfollow/{uid}"})
    Result follow(@PathVariable Integer uid, HttpServletRequest request) {
        Integer id = AutUtil.self().getUid();
        boolean b = request.getRequestURI().contains("/follow");
        return Result.judge(service.follow(id,uid,b));
    }
}
