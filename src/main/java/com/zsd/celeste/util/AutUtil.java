package com.zsd.celeste.util;

import com.zsd.celeste.entity.DO.LoginUser;
import com.zsd.celeste.entity.PO.User;
import com.zsd.celeste.entity.PO.UserPojo;
import com.zsd.celeste.exception.exception.UserNotLoginEx;
import com.zsd.celeste.util.base.service.BaseService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AutUtil {


    static public Integer uid(){
        return self().getUid();
    }
    static public LoginUser self(){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return (LoginUser) authentication.getPrincipal();
        }catch (Exception e){
            throw new UserNotLoginEx();
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

//    public boolean isSelf(BaseService<? extends UserPojo> service,Integer id){
//        self();
//        Integer uid = getLoginUser().getUid();
//        UserPojo pojo = service.getById(id);
//        if (!Objects.equals(pojo.getUid(), uid))
//            throw new RuntimeException("无法修改他人数据");
//        return true;
//    }
    public boolean isRoot(){
        self();
        LoginUser user = getLoginUser();
        if (!user.getUsername().equals("root"))
            throw new RuntimeException("没有 root 权限");
        return true;
    }

}
