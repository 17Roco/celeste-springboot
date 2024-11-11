package com.zsd.celeste.util;

import com.zsd.celeste.entity.form.LoginUser;
import com.zsd.celeste.enums.Role;
import com.zsd.celeste.exception.exception.RoleEx;
import com.zsd.celeste.exception.exception.UserNotLoginEx;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;

@Component
public class AutUtil {

    /**
    * 获取当前登录用户信息
    * */
    static public LoginUser getAuthentication(){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return (LoginUser) authentication.getPrincipal();
        }catch (Exception e){
            return null;
        }
    }

    /**
    * 判断当前是否已登录
    * */
    static public boolean isLogin(){
        return !Objects.isNull(getAuthentication());
    }

    /**
    * 如果登录则执行传入的函数
    * */
    static public void login(FuntionInterface function){
        if (isLogin()){
            function.apply();
        }
    }



    /**
    * 获取当前登录用户信息,如果未登录则抛出异常
    * */
    static public LoginUser self(){
        LoginUser loginUser = getAuthentication();
        if (Objects.isNull(loginUser))
            throw new UserNotLoginEx();
        return loginUser;
    }

    /**
     * 获取当前登录用户的uid,如果未登录则抛出异常
     * */
    static public Integer uid(){
        return self().getUid();
    }

    /**
     * 获取当前登录用户的角色,如果未登录则抛出异常
     * */
    static public Role getRole(){
        return self().getRole();
    }

    static public boolean isAdmin(){
        if (!getRole().equals(Role.ADMIN))
            throw new RoleEx();
        return true;
    }



    /**
    * 登录用户信息
    * */
    public LoginUser getLoginUser(){
        return self();
    }
    public Integer   getUid(){
        return getLoginUser().getUid();
    }
    public String    getToken(){
        return getLoginUser().getToken();
    }
    public boolean needLogin(){
        self();
        return true;
    }

}
