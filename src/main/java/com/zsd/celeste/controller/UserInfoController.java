package com.zsd.celeste.controller;

import com.zsd.celeste.entity.form.ChangePasswordForm;
import com.zsd.celeste.entity.form.UserInfoForm;
import com.zsd.celeste.service.UserService;
import com.zsd.celeste.util.AutUtil;
import com.zsd.celeste.util.result.Result;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;


@RestController()
@RequestMapping("/user")
public class UserInfoController {

    @Autowired
    private UserService service;



    /**
     * 获取用户信息
     * */
    @GetMapping("/{id}")
    Result getUser(@PathVariable Integer id) {
        return Result.ok(service.getById(id));
    }


    /**
     * 更新用户信息 username,sex,birthday,sign
     * */
    @PreAuthorize("@autUtil.needLogin()")
    @PutMapping("/info")
    Result updateUserInfo(@RequestBody UserInfoForm form) {
        return Result.judge(service.updateBySelf(form));
    }

    /**
     * 更新头像
     * */
    @PreAuthorize("@autUtil.needLogin()")
    @PutMapping("/img")
    Result upload(@RequestParam("file") MultipartFile file) {
        return Result.ok(service.updateImg(file));
    }

    /**
     * 更新密码
     * */
    @PreAuthorize("@autUtil.needLogin()")
    @PutMapping("/pw")
    Result changePassword(@RequestBody ChangePasswordForm update){
        return Result.judge(service.updatePassword(AutUtil.self().getUsername(), update.getOldPassword(), update.getNewPassword()));
    }



    /**
     * 获取关注列表
     * */
    @GetMapping({"/follow/{id}","/follow/{id}/{index}"})
    Result getUserFollow(@PathVariable Integer id, @PathVariable(required = false) Integer index) {
        return Result.ok(service.getFollow(id,index == null ? 1 : index));
    }

    /**
     * 关注、取消关注
     * */
    @PreAuthorize("@autUtil.needLogin()")
    @PostMapping("/follow/{uid}")
    Result follow(@PathVariable Integer uid) {
        return follow(uid,true);
    }
    @PreAuthorize("@autUtil.needLogin()")
    @PostMapping("/unfollow/{uid}")
    Result unfollow(@PathVariable Integer uid) {
        return follow(uid,false);
    }

    Result follow(@PathVariable Integer uid,boolean b) {
        Integer id = AutUtil.self().getUid();
        return Result.judge(service.follow(id,uid,b));
    }
}

