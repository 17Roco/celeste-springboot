package com.zsd.celeste.util;

import com.zsd.celeste.entity.DO.LoginUser;
import com.zsd.celeste.entity.PO.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AutUtil {


    static public LoginUser self(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            return (LoginUser) authentication.getPrincipal();
        }catch (Exception e){
            throw new RuntimeException("需要登录");
        }
    }
    public LoginUser getLoginUser(){
        return self();
    }
    public String getToken(){
        return getLoginUser().getToken();
    }
    public boolean needLogin(){
        if (Objects.isNull(getLoginUser()))
            throw new RuntimeException("需要登陆");
        return true;
    }

    public boolean self(Integer uid){
        needLogin();
        LoginUser user = getLoginUser();
        if (!user.getUid().equals(uid))
            throw new RuntimeException("无法修改他人数据");
        return true;
    }
    public boolean root(){
        needLogin();
        LoginUser user = getLoginUser();
        if (!user.getUsername().equals("root"))
            throw new RuntimeException("没有 root 权限");
        return true;
    }
}
