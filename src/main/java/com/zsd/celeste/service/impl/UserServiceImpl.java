package com.zsd.celeste.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsd.celeste.mapper.UserMapper;
import com.zsd.celeste.pojo.Article;
import com.zsd.celeste.pojo.LoginUser;
import com.zsd.celeste.pojo.User;
import com.zsd.celeste.service.ArticleService;
import com.zsd.celeste.service.UserService;
import com.zsd.celeste.service.base.CBaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * (User)表服务实现类
 *
 * @author zsd
 * @since 2024-06-07 00:43:07
 */
@Service
public class UserServiceImpl extends CBaseServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private ArticleService articleService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getOne(new QueryWrapper<User>().eq("username", username));
        if(Objects.isNull(user))
            throw new RuntimeException("用户不存在");
        return new LoginUser(user);
    }

    @Override
    public List<Article> getArticleByUser(Integer uid, int index) {
        return articleService.selectEq("uid", uid, index).getRecords();
    }
}

