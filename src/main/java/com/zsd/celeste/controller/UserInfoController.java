package com.zsd.celeste.controller;

import com.zsd.celeste.entity.DO.LoginForm;
import com.zsd.celeste.entity.PO.User;
import com.zsd.celeste.entity.VO.UserInfoVo;
import com.zsd.celeste.service.UserService;
import com.zsd.celeste.util.AutUtil;
import com.zsd.celeste.util.PojoUtil;
import com.zsd.celeste.util.result.DataResult;
import com.zsd.celeste.util.result.Result;
import com.zsd.celeste.util.result.StreamResult;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

/**
 * (User)表控制层
 *
 * @author zsd
 * @since 2024-06-07 00:43:08
 */
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
        return DataResult.ok(service.needInfoById(id));
    }


    /**
     * 更新用户信息 username,sex,birthday,sign
     * */
    @PreAuthorize("@autUtil.needLogin()")
    @PostMapping("/info")
    Result updateUserInfo(@RequestBody UserInfoVo vo) {
        return Result.judge(service.updateInfo(AutUtil.uid(),vo));
    }

    @PreAuthorize("@autUtil.needLogin()")
    @PostMapping("/img")
    Result upload(@RequestParam("file") MultipartFile file) {
        return DataResult.ok(service.updateImg(file));
    }


    /**
     * 获取关注列表
     * 关注
     * 取消关注
     * */
    Result follow(@PathVariable Integer uid,boolean b) {
        Integer id = AutUtil.self().getUid();
        return Result.judge(service.follow(id,uid,b));
    }

    @GetMapping("/{id}/follow")
    Result getUserFollow(@PathVariable Integer id) {
        return DataResult.ok(service.getFollow(id));
    }

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

}

