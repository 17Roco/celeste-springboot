package com.zsd.celeste.controller;

import com.zsd.celeste.entity.DO.LoginForm;
import com.zsd.celeste.entity.PO.User;
import com.zsd.celeste.entity.VO.UserInfoVo;
import com.zsd.celeste.service.UserService;
import com.zsd.celeste.util.AutUtil;
import com.zsd.celeste.util.result.DataResult;
import com.zsd.celeste.util.result.Result;
import com.zsd.celeste.util.result.StreamResult;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RestController()
@RequestMapping("/user")
public class UserInfoController {

    @Autowired
    private UserService service;
    @Autowired
    private AutUtil autUtil;

    @GetMapping("/{id}")
    Result getUser(@PathVariable Integer id) {
        User po = service.getById(id);
        if (Objects.isNull(po))
            return DataResult.error("找不到用户");
        UserInfoVo vo = new UserInfoVo();
        BeanUtils.copyProperties(po, vo);
        return DataResult.ok(vo);
    }

    @PreAuthorize("@autUtil.needLogin()")
    @PutMapping()
    Result updateUser(@RequestBody UserInfoVo userInfoVo){
        userInfoVo.setUid(autUtil.getLoginUser().getUid());
        return Result.judge(service.updateInfo(userInfoVo));
    }
}

