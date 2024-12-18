package com.zsd.celeste.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zsd.celeste.entity.form.LoginUser;
import com.zsd.celeste.entity.PO.User;
import com.zsd.celeste.entity.form.UserInfoForm;
import com.zsd.celeste.util.AutUtil;
import com.zsd.celeste.util.base.BasePojoService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * (User)表服务接口
 *
 * @author zsd
 * @since 2024-06-07 00:43:07
 */
public interface UserService extends BasePojoService<User>, UserDetailsService {

    default  UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByUsername(username);
        return new LoginUser(need(user),null);
    }

    default User getUserByUsername(String username) {
        return getOne(new QueryWrapper<User>().eq("username", username));
    }



    /**
     * 获取用户信息 + 是否关注
     * */
    default User needById(Serializable id) {
        User user = BasePojoService.super.needById(id);
        // 是否有关注该用户
        user.setIsFollow(isFollow(AutUtil.uid(),user.getUid()));
        return user;
    }
    default User getById(Serializable id) {
        User user = BasePojoService.super.getById(id);
        if (AutUtil.isLogin()) {
            user.setIsFollow(isFollow(AutUtil.uid(),user.getUid()));
        }
        return user;
    }


    /**
     * 更新基本信息
     * 更新密码
     * 更新头像
     * */
    boolean updateBySelf(UserInfoForm form);
    boolean updatePassword(String username, String oldPassword, String newPassword);
    String updateImg(MultipartFile file);


    /**
     * 关注
     * 获取列表
     * 是否关注
    * */
    IPage<User> getFollow(Integer id, Integer index);
    IPage<User> getFollowed(Integer id, Integer index);

    boolean follow(Integer id, Integer uid,boolean b);
    boolean isFollow(Integer id,Integer uid);

}

