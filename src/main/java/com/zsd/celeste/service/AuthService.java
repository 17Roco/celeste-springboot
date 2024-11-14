package com.zsd.celeste.service;

import com.zsd.celeste.entity.PO.User;
import com.zsd.celeste.entity.form.LoginUser;

public interface AuthService {


    /**
     * 登录
     * @param username 用户名
     * @param password 密码
     * @return token
     * */
    String login(String username, String password);

    /**
     * 登出
     * @param token token
     * */
    boolean logout(String token);

    /**
     * 注册
     * @param username 用户名
     * */
    boolean register(String username, String password);

    /**
     * 获取当前用户信息
     * @return 用户信息
     * */
    User getSelf();

    User getLoginUser(String token);
}
