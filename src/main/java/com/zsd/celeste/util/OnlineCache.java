package com.zsd.celeste.util;

import com.zsd.celeste.pojo.LoginUser;
import com.zsd.celeste.pojo.User;
import com.zsd.celeste.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Component
public class OnlineCache {

    @Autowired
    private UserService service;
    private final Map<Object , LoginUser> onlineUser = new HashMap<>();

    public void addTestUser(LoginUser user,String  token){
        onlineUser.put(token,user);
    }

    private String  createToken(){
        String token = UUID.randomUUID().toString();
        if(!onlineUser.containsKey(token))
            return token;
        else
            return createToken();
    }

    /**
     * 用户登录，创建一个token
     * */
    public String userLogin(LoginUser user){
        String token = createToken();
        user.setToken(token);
        onlineUser.put(token,user);
        return token;
    }
    /**
     * 用户退出，删除对应token
    * */
    public boolean userLogout(String token){
        if (!onlineUser.containsKey(token))
            return false;
        onlineUser.remove(token);
        return true;
    }
    /**
     * 根据token获取用户
     * */
    public LoginUser getUser(String token){
        LoginUser self = onlineUser.get(token);
        if (Objects.isNull(self)) return self;
        self.setUser(service._selectOne(w -> w.eq("uid", self.getUser().getUid())));
        return self;
    }
}
