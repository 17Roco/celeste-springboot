package com.zsd.celeste.service.impl;

import com.zsd.celeste.entity.PO.User;
import com.zsd.celeste.service.TokenService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Service
public class TokenServiceImpl implements TokenService {

    final private Map<String, User> cache = new HashMap<String, User>();

    private String createToken() {
        return UUID.randomUUID().toString();
    }


    public void addToken(User user) {
        if (Objects.isNull(user))
            throw new RuntimeException("添加token异常");
        cache.put(createToken(), user);
    }
    public void removeToken(String token) {
        if (Objects.isNull(cache.remove(token)))
            throw new RuntimeException("删除token异常");
    }
    public User getUser(String token) {
        return cache.get(token);
    }
}
