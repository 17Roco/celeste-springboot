package com.zsd.celeste.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zsd.celeste.pojo.Article;
import com.zsd.celeste.pojo.LoginUser;
import com.zsd.celeste.pojo.User;
import com.zsd.celeste.service.base.CBaseService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Objects;

/**
 * (User)表服务接口
 *
 * @author zsd
 * @since 2024-06-07 00:43:07
 */
public interface UserService extends CBaseService<User>, UserDetailsService {

    default  UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getOne(new QueryWrapper<User>().eq("username", username));
        if(Objects.isNull(user))
            throw new RuntimeException("用户不存在");
        return new LoginUser(user,null);
    }


}

