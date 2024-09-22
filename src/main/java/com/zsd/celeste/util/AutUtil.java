package com.zsd.celeste.util;

import com.zsd.celeste.entity.DO.LoginUser;
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
            return null;
        }
    }
    public LoginUser getLoginUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            return (LoginUser) authentication.getPrincipal();
        }catch (Exception e){
            return null;
        }
    }
    public boolean needLogin(){
        LoginUser user = getLoginUser();
        if (Objects.isNull(user))
            throw new RuntimeException("需要登陆");
        else if (user.getToken().equals("notoken"))
            throw new RuntimeException("token 失效");
        return true;
    }

    public boolean self(Integer uid){
        needLogin();
        LoginUser user = getLoginUser();
        boolean equals = Objects.equals(user.getUser().getUid(), uid);
        if (equals){
            return true;
        }else {
            throw new RuntimeException("无法修改其他用户信息");
        }
    }
    public boolean root(){
        needLogin();
        boolean root = Objects.equals(getLoginUser().getUsername(), "root");
        if (root){
            return true;
        }else {
            throw new RuntimeException("没有 root 权限");
        }
    }
}
