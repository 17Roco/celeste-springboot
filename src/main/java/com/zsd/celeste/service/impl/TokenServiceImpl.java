package com.zsd.celeste.service.impl;

import com.zsd.celeste.entity.PO.User;
import com.zsd.celeste.service.TokenService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TokenServiceImpl implements TokenService {

    final private Map<String, User> cache = new HashMap<>();
    final private Map<Integer, List<String>> userTokenCache = new HashMap<>();

    private String createToken() {
        return UUID.randomUUID().toString();
    }

    public TokenServiceImpl(){
        System.out.println(" ----------------- >>>> token service");
        User user = new User();
        user.setUsername("test_user");
        user.setUid(0);
        cache.put("12345678",user);
    }

    public String addToken(User user) {
        if (Objects.isNull(user))
            throw new RuntimeException("添加token异常");
        // 添加token到缓存表里
        String token = createToken();
        cache.put(token, user);
        // 添加token到反向缓存表里
        userTokenCache.computeIfAbsent(user.getUid(), k -> new ArrayList<>());
        userTokenCache.get(user.getUid()).add(token);
        return token;
    }
    public void removeToken(String token) {
        if (Objects.isNull(cache.remove(token)))
            throw new RuntimeException("删除token异常");
    }
    public User getUser(String token) {
        return cache.get(token);
    }


    public void removeUser(User user) {
        List<String> tokens = userTokenCache.remove(user.getUid());
        tokens.forEach(cache::remove);
    }
}
