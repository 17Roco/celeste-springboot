package com.zsd.celeste.service;

import com.zsd.celeste.entity.PO.User;

import java.util.Objects;

public interface TokenService {

    void addToken(User user);
    void removeToken(String token);
    User getUser(String token);
}
