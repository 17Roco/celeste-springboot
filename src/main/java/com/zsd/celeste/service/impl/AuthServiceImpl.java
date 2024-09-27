package com.zsd.celeste.service.impl;

import com.zsd.celeste.entity.DO.LoginUser;
import com.zsd.celeste.service.AuthService;
import lombok.Setter;
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
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager manager;

    /**
     * 验证信息
     * */
    @Override
    public LoginUser auth(String username, String password) {
        // 验证用户
        Authentication authenticate = manager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        LoginUser u = (LoginUser) authenticate.getPrincipal();
        // 判断用户认证
        if (Objects.isNull(u))
            throw new RuntimeException("用户认证失败");
        return u;
    }
}
