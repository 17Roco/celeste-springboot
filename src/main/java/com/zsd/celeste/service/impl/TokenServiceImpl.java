package com.zsd.celeste.service.impl;

import com.zsd.celeste.entity.DO.LoginUser;
import com.zsd.celeste.entity.PO.User;
import com.zsd.celeste.service.TokenService;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Getter
public class TokenServiceImpl implements TokenService {

    final private Map<String, LoginUser> cache = new HashMap<>();
    final private Map<Integer, List<String>> reCache = new HashMap<>();

    private String createToken() {
        return UUID.randomUUID().toString();
    }

    public TokenServiceImpl(){
        System.out.println("test_user token : 123456");
        User user = new User();
        user.setUsername("test_user");
        user.setUid(0);
        user.setStatus(1);
        cache.put("123456",new LoginUser(user,"123456"));
    }

    /**
     * 添加
     * */
    public String addToken(User user) {
        String token = createToken();
        // 添加token到缓存表里
        cache.put(token, new LoginUser(user,token));
        // 添加token到反向缓存表里
        reCache.computeIfAbsent(user.getUid(), k -> new ArrayList<>());
        reCache.get(user.getUid()).add(token);
        // 返回 token
        return token;
    }

    /**
     * 删除
     * */
    public void removeToken(String token) {
        if (Objects.isNull(cache.remove(token)))
            throw new RuntimeException("删除token异常");
    }
    public void removeUser(User user) {
        List<String> tokens = reCache.remove(user.getUid());
        tokens.forEach(cache::remove);
    }


    /**
     * 获取用户
     * */
    public LoginUser getUser(String token) {
        return cache.get(token);
    }

}
