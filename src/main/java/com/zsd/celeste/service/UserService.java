package com.zsd.celeste.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zsd.celeste.entity.VO.UserInfoVo;
import com.zsd.celeste.entity.DO.LoginUser;
import com.zsd.celeste.entity.PO.User;
import com.zsd.celeste.util.PojoUtil;
import com.zsd.celeste.util.base.service.BaseService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * (User)表服务接口
 *
 * @author zsd
 * @since 2024-06-07 00:43:07
 */
public interface UserService extends BaseService<User>, UserDetailsService {

    default  UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getOne(new QueryWrapper<User>().eq("username", username));
        if(Objects.isNull(user))
            throw new RuntimeException("用户不存在");
        return new LoginUser(user,null);
    }


    void setPasswordEncoder(PasswordEncoder passwordEncoder);
    void setManager(AuthenticationManager manager);





    String login(String username, String password);
    boolean logout(String token);
    boolean register(String username, String password);
    boolean updatePassword(String username, String oldPassword, String newPassword);
    boolean updateInfo(UserInfoVo userInfo);

    // follow
    boolean follow(Integer id, Integer uid,boolean b);
    List<UserInfoVo> getFollow(Integer id);


    default UserInfoVo getUserInfo(Integer id){
        User po = getById(id);
        return PojoUtil.copy(new UserInfoVo(),po);
    }
    default UserInfoVo needInfoById(Serializable id) {
        return PojoUtil.copy(new UserInfoVo(),BaseService.super.needById(id));
    }
}

