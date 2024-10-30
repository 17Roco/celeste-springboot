package com.zsd.celeste.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zsd.celeste.entity.VO.UserInfoVo;
import com.zsd.celeste.entity.DO.LoginUser;
import com.zsd.celeste.entity.PO.User;
import com.zsd.celeste.util.PojoUtil;
import com.zsd.celeste.util.base.service.BasePojoService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * (User)表服务接口
 *
 * @author zsd
 * @since 2024-06-07 00:43:07
 */
public interface UserService extends BasePojoService<User>, UserDetailsService {

    default  UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getOne(new QueryWrapper<User>().eq("username", username));
        return new LoginUser(need(user),null);
    }


    void setPasswordEncoder(PasswordEncoder passwordEncoder);
    void setManager(AuthenticationManager manager);

    /**
     * 获取用户信息
     * */
    default UserInfoVo getUserInfo(Integer id){
        User po = getById(id);
        return PojoUtil.copy(new UserInfoVo(),po);
    }
    default UserInfoVo needInfoById(Integer id) {
        return PojoUtil.copy(new UserInfoVo(), BasePojoService.super.needById(id));
    }

    /**
     * 登录
     * 登出
     * 注册
     * */
    String login(String username, String password);
    boolean logout(String token);
    boolean register(String username, String password);

    /**
     * 更新基本信息
     * 更新密码
     * 更新头像
     * */
    boolean updateInfo(Integer uid,UserInfoVo userInfo);
    boolean updatePassword(String username, String oldPassword, String newPassword);
    String updateImg(MultipartFile file);


    /**
     * 关注
     * 获取列表
    * */
    boolean follow(Integer id, Integer uid,boolean b);
    List<UserInfoVo> getFollow(Integer id);


}

