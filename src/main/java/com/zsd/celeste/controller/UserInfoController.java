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
     * @param id 用户id
     * */
    @GetMapping("/{id}")
    Result getUser(@PathVariable Integer id) {
        return Result.ok(service.getById(id));
    }


    /**
     * 更新用户信息 username,sex,birthday,sign
     * @param form 用户信息表单
     * */
    @PutMapping("/info")
    Result updateUserInfo(@RequestBody UserInfoForm form) {
        return Result.judge(service.updateBySelf(form));
    }

    /**
     * 更新头像
     * @param file 头像文件
     * */
    @PutMapping("/img")
    Result upload(@RequestParam("file") MultipartFile file) {
        return Result.ok(service.updateImg(file));
    }

    /**
     * 更新密码
     * @param update 密码更新表单
     * */
    @PutMapping("/pw")
    Result changePassword(@RequestBody ChangePasswordForm update){
        return Result.judge(service.updatePassword(AutUtil.self().getUsername(), update.getOldPassword(), update.getNewPassword()));
    }



    /**
     * 获取关注列表
     * @param id 用户id
     * @param index 页码
     * */
    @GetMapping("/follow/{id}/{index}")
    Result getUserFollow(@PathVariable Integer id, @PathVariable Integer index) {
        return Result.ok(service.getFollow(id,index));
    }

    /**
     * 关注、取消关注
     * @param uid 被关注用户id
     * */
    @PostMapping("/follow/{uid}")
    Result follow(@PathVariable Integer uid) {
        return follow(uid,true);
    }
    @PostMapping("/unfollow/{uid}")
    Result unfollow(@PathVariable Integer uid) {
        return follow(uid,false);
    }

    Result follow(@PathVariable Integer uid,boolean b) {
        Integer id = AutUtil.self().getUid();
        return Result.judge(service.follow(id,uid,b));
    }
}

