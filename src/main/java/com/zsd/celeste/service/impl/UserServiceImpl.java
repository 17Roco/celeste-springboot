package com.zsd.celeste.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsd.celeste.entity.DO.LoginUser;
import com.zsd.celeste.entity.VO.UserInfoVo;
import com.zsd.celeste.mapper.UserMapper;
import com.zsd.celeste.entity.PO.User;
import com.zsd.celeste.service.TokenService;
import com.zsd.celeste.service.UserService;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Objects;

/**
 * (User)表服务实现类
 *
 * @author zsd
 * @since 2024-06-07 00:43:07
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    TokenService tokenService;

    @Setter
    PasswordEncoder passwordEncoder;
    @Setter
    AuthenticationManager manager;

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

    @Override
    public String login(String username, String password) {
        // 验证
        LoginUser auth = auth(username, password);
        // 生成并返回token
        return tokenService.addToken(auth.getUser());
    }

    @Override
    public boolean logout(String token) {
        tokenService.removeToken(token);
        return true;
    }

    @Override
    public boolean register(String username, String password) {
        if (exists(new QueryWrapper<User>().eq("username",username)))
            throw new RuntimeException("用户名已存在");
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        return save(user);
    }

    @Override
    public boolean updatePassword(String username, String oldPassword, String newPassword) {
        LoginUser auth = auth(username, oldPassword);
        User user = auth.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        if (!save(user))
            throw new RuntimeException("修改失败");
        tokenService.removeUser(user);
        return true;
    }

    @Override
    public boolean updateInfo(UserInfoVo userInfo) {
        User user = new User();
        BeanUtils.copyProperties(userInfo,user);
        return updateById(user);
    }
}

