package com.zsd.celeste.service.data;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zsd.celeste.entity.PO.User;
import com.zsd.celeste.entity.form.LoginUser;
import com.zsd.celeste.entity.form.UserInfoForm;
import com.zsd.celeste.util.base.BaseService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.multipart.MultipartFile;

/**
 * (User)表服务接口
 *
 * @author zsd
 * @since 2024-06-07 00:43:07
 */
public interface UserService extends BaseService<User>, UserDetailsService {

    /**
     * 登录
     * */
    default  UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByUsername(username);
        return new LoginUser(need(user),null);
    }

    /**
     * 根据用户名获取用户
     * */
    default User getUserByUsername(String username) {
        return getOne(new QueryWrapper<User>().eq("username", username));
    }



    /**
     * 更新基本信息
     * 更新密码
     * 更新头像
     * */
    User updateInfo(Integer uid, UserInfoForm form);
    User updatePassword(Integer uid,String newPassword);
    String updateImg(Integer uid,MultipartFile file);



}

