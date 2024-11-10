package com.zsd.celeste.service.impl;

import com.zsd.celeste.entity.form.LoginUser;
import com.zsd.celeste.entity.PO.User;
import com.zsd.celeste.service.TokenService;
import com.zsd.celeste.service.UserService;
import com.zsd.celeste.util.redis.RedisTokenHash;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private RedisTokenHash cache;

    private String createToken() {
        return UUID.randomUUID().toString();
    }


    public Map<String, Object> getMap() {
        return cache.getMap();
    }

    /**
     * 获取用户
     * */
    public Integer getUid(String token) {
        return (Integer) cache.get(token);
    }

    /**
     * 添加
     * */
    public String addToken(Integer uid) {
        String token = createToken();
        // 添加token到缓存表里
        cache.put(token, uid);
        // 返回 token
        return token;
    }

    /**
     * 删除
     * */
    public void removeToken(String token) {
        cache.delete(token);
    }
    public void removeUid(Integer uid) {
        cache.reDelete(uid);
    }


    public LoginUser getUser(String token){
        Integer uid = getUid(token);
        if (uid == null)
            return null;
        User user = new User();
        user.setUid(uid);
        return new LoginUser(user, token);
    }

}
