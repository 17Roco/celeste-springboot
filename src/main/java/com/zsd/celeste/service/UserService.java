package com.zsd.celeste.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zsd.celeste.pojo.Article;
import com.zsd.celeste.pojo.User;
import com.zsd.celeste.service.base.CBaseService;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * (User)表服务接口
 *
 * @author zsd
 * @since 2024-06-07 00:43:07
 */
public interface UserService extends CBaseService<User>, UserDetailsService {
    List<Article> getArticleByUser(Integer uid, int index);


}

