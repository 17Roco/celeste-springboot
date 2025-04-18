package com.zsd.celeste.service.common.impl;

import com.zsd.celeste.entity.PO.User;
import com.zsd.celeste.entity.form.LoginUser;
import com.zsd.celeste.exception.exception.LoginEx;
import com.zsd.celeste.service.common.AuthService;
import com.zsd.celeste.service.common.TokenService;
import com.zsd.celeste.service.data.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    PasswordEncoder passwordEncoder;


    /**
     * 验证信息
     * */
    public LoginUser auth(String username, String password) {
        // 验证用户
        Authentication authenticate = manager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        LoginUser u = (LoginUser) authenticate.getPrincipal();
        // 判断用户认证
        if (Objects.isNull(u))
            throw new LoginEx();
        return u;
    }




    /**
     * 登录 验证用户信息并生成token
     * @param username 用户名
     * @param password 密码
     * */
    public String login(String username, String password) {
        // 验证
        LoginUser auth = auth(username, password);
        // 生成并返回token
        return tokenService.addToken(auth.getUser().getUid());
    }

    /**
     * 登出
     * */
    public boolean logout(String token) {
        // 删除token
        tokenService.removeToken(token);
        return true;
    }

    /**
     * 注册
     * */
    public boolean register(String username, String password) {
        // 验证用户名是否存在
        if (userService.getUserByUsername(username) != null)
            throw new LoginEx("用户名已存在");
        // 创建用户
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        // 保存用户
        return userService.save(user);
    }


    /**
     * 根据token获取用户信息
     * @param token token
     * */
    public User getLoginUser(String token){
        Integer uid = tokenService.getUid(token);
        if (uid == null)
            return null;
        return userService.getById(uid);
    }
}
