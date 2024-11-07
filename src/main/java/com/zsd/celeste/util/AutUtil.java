package com.zsd.celeste.util;

import com.zsd.celeste.entity.form.LoginUser;
import com.zsd.celeste.exception.exception.UserNotLoginEx;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.function.Function;

@Component
public class AutUtil {


    static public Integer uid(){
        return self().getUid();
    }
    static public LoginUser self(){
        LoginUser loginUser = getAuthentication();
        if (Objects.isNull(loginUser))
            throw new UserNotLoginEx();
        return loginUser;
    }
    static public LoginUser getAuthentication(){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return (LoginUser) authentication.getPrincipal();
        }catch (Exception e){
            return null;
        }
    }
    static public boolean isLogin(){
        return !Objects.isNull(getAuthentication());
    }

    static public void login(Function<Void,Void> function){
        if (isLogin()){
            function.apply(null);
        }
    }


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


    public boolean isRoot(){
        self();
        LoginUser user = getLoginUser();
        if (!user.getUsername().equals("root"))
            throw new RuntimeException("没有 root 权限");
        return true;
    }

}
