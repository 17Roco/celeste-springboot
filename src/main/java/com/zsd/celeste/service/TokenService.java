package com.zsd.celeste.service;

import com.zsd.celeste.entity.form.LoginUser;
import com.zsd.celeste.entity.PO.User;

import java.util.List;
import java.util.Map;

public interface TokenService {

    Map<String,Object> getMap();


    Integer getUid(String token);
    String addToken(Integer uid);
    void removeToken(String token);
    void removeUid(Integer uid);
}
