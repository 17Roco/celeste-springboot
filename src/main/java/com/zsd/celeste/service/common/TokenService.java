package com.zsd.celeste.service.common;

import java.util.Map;

public interface TokenService {

    Map<String,Object> getMap();


    Integer getUid(String token);
    String addToken(Integer uid);
    void removeToken(String token);
    void removeUid(Integer uid);
}
