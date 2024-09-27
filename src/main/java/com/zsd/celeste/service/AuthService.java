package com.zsd.celeste.service;

import com.zsd.celeste.entity.DO.LoginUser;

public interface AuthService {
    LoginUser auth(String username, String password);
}
