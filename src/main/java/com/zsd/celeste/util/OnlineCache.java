package com.zsd.celeste.util;

import com.zsd.celeste.pojo.LoginUser;
import com.zsd.celeste.pojo.User;
import com.zsd.celeste.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class OnlineCache {
    private final Map<String , LoginUser> onlineUser = new HashMap<>();


    private String  createToken(){
        String token = UUID.randomUUID().toString();
        if(!onlineUser.containsKey(token))
            return token;
        else
            return createToken();
    }
    public String userLogin(LoginUser user){
        String token = createToken();
        onlineUser.put(token,user);
        return token;
    }
    public boolean userLogout(String token){
        if(!onlineUser.containsKey(token))
            return false;
        onlineUser.remove(token);
        return true;
    }

    @Autowired
    private UserService service;

    public LoginUser getUser(String token){
//        return onlineUser.get(token);
        // 测试使用，token == uid
        return new LoginUser(service.getById(Integer.valueOf(token)));//
    }
}
