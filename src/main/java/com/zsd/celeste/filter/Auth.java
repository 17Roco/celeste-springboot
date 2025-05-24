package com.zsd.celeste.filter;

import com.zsd.celeste.entity.PO.User;
import com.zsd.celeste.entity.form.LoginUser;
import com.zsd.celeste.exception.exception.UserNotLoginEx;
import com.zsd.celeste.service.data.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class Auth {

    @Autowired
    private UserService service;

    private LoginUser getUser(){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return (LoginUser) authentication.getPrincipal();
        }catch (Exception e){
            throw new UserNotLoginEx();
        }
    }

    public boolean needUser(User user){
        // 验证用户是否登录
        user = getUser().getUser();
        return true;
    }
}
