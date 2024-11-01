package com.zsd.celeste.service;

import com.zsd.celeste.entity.form.LoginUser;
import com.zsd.celeste.entity.PO.User;

import java.util.List;
import java.util.Map;

public interface TokenService {

    Map<String, LoginUser> getCache();
    Map<Integer, List<String>> getReCache();


    String  addToken(User user);
    void removeToken(String token);
    LoginUser getUser(String token);
    void removeUser(User user);
}
