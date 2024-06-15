package com.zsd.celeste.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zsd.celeste.mapper.UserMapper;
import com.zsd.celeste.pojo.LoginUser;
import com.zsd.celeste.pojo.User;
import com.zsd.celeste.service.UserService;
import com.zsd.celeste.service.base.CBaseServiceImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * (User)表服务实现类
 *
 * @author zsd
 * @since 2024-06-07 00:43:07
 */
@Service
public class UserServiceImpl extends CBaseServiceImpl<UserMapper, User> implements UserService {

}

