package com.zsd.celeste.controller.data;

import com.zsd.celeste.entity.form.ChangePasswordForm;
import com.zsd.celeste.entity.form.UserInfoForm;
import com.zsd.celeste.service.common.AuthService;
import com.zsd.celeste.service.data.UserService;
import com.zsd.celeste.util.result.Result;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;


@RestController()
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;
    @Autowired
    private AuthService authService;

    /**
     * 获取用户信息
     * @param id 用户id
     * */
    @GetMapping("/{id}")
    Result getUser(@PathVariable Integer id) {
        return Result.ok(service.getById(id));
    }

    /**
     * 获取当前用户信息
     * */
    @GetMapping("/self")
    Result self() {
        // todo get self
//        return Result.ok(service.g);
        return null;
    }


    /**
     * 更新用户信息 username,sex,birthday,sign
     * @param form 用户信息表单
     * */
    @PutMapping("/info")
    Result updateUserInfo(@RequestBody UserInfoForm form) {
        // get uid
        return Result.judge(service.updateInfo(null,form));
    }

    /**
     * 更新头像
     * @param file 头像文件
     * */
    @PutMapping("/img")
    Result upload(@RequestParam("file") MultipartFile file) {
        // get uid
        return Result.ok(service.updateImg(null,file));
    }

    /**
     * 更新密码
     * @param update 密码更新表单
     * */
    @PutMapping("/pw")
    Result changePassword(@RequestBody ChangePasswordForm update){
        // 验证旧密码
        // todo get username
        authService.auth(null, update.getOldPassword());
        // 更新新密码
        // todo get uid
        return Result.judge(service.updatePassword(null, update.getNewPassword()) != null);
    }

}

