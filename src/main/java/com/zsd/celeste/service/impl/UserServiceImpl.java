package com.zsd.celeste.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsd.celeste.entity.form.LoginUser;
import com.zsd.celeste.entity.VO.UserInfoVo;
import com.zsd.celeste.enums.ResourceNameSpace;
import com.zsd.celeste.mapper.UserMapper;
import com.zsd.celeste.entity.PO.User;
import com.zsd.celeste.service.FileResourceService;
import com.zsd.celeste.service.TokenService;
import com.zsd.celeste.service.UserService;
import com.zsd.celeste.util.AutUtil;
import com.zsd.celeste.util.PojoUtil;
import com.zsd.celeste.util.link.LinkConfig;
import com.zsd.celeste.util.link.LinkMapper;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    @Autowired
    private FileResourceService fileResourceService;
    @Setter
    PasswordEncoder passwordEncoder;
    @Setter
    AuthenticationManager manager;
    @Autowired
    private LinkMapper linkMapper;
    public String getResourceMsg() {
        return "用户不存在";
    }
    final private LinkConfig followConfig = new LinkConfig("link_user_follow","id","uid");

    /**
     * 验证信息
     * */
    public LoginUser auth(String username, String password) {
        // 验证用户
        Authentication authenticate = manager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        LoginUser u = (LoginUser) authenticate.getPrincipal();
        // 判断用户认证
        if (Objects.isNull(u))
            throw new RuntimeException("用户认证失败");
        return u;
    }




    /**
     * 登录
     * */
    public String login(String username, String password) {
        // 验证
        LoginUser auth = auth(username, password);
        // 生成并返回token
        return tokenService.addToken(auth.getUser());
    }

    /**
    * 登出
    * */
    public boolean logout(String token) {
        tokenService.removeToken(token);
        return true;
    }

    /**
     * 注册
     * */
    public boolean register(String username, String password) {
        if (exists(new QueryWrapper<User>().eq("username",username)))
            throw new RuntimeException("用户名已存在");
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        return save(user);
    }








    /**
     * 更新信息,username,sex,birthday,sign
     * */
    public boolean updateInfo(Integer uid,UserInfoVo userInfo) {
        User user = new User(uid, userInfo);
        return updateById(user);
    }
    /**
     * 修改密码
     * */
    public boolean updatePassword(String username, String oldPassword, String newPassword) {
        LoginUser auth = auth(username, oldPassword);
        User user = auth.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        if (!updateById(user))
            throw new RuntimeException("修改失败");
        tokenService.removeUser(user);
        return true;
    }
    /**
     * 更新头像
     * */
    public String updateImg(MultipartFile file) {
        // 获取用户
        User user = needById(AutUtil.uid());
        // 保存图片
        String img = fileResourceService.saveImg(file, ResourceNameSpace.IMAGE_USER);
        // 修改并保存
        user.setImg(img);
        save(user);
        return img;
    }















    /**
     * 关注用户
     * */
    public boolean follow(Integer id, Integer uid,boolean b) {
        User self = needById(id);
        User user = needById(uid);
        self.setFollow(self.getFollow()+(b?1:-1));
        user.setFollower(self.getFollower()+(b?1:-1));
        return b ?
                linkMapper.addLink(followConfig,id,uid) && updateById(self) && updateById(user)
                :
                linkMapper.delLink(followConfig,id,uid) && updateById(self) && updateById(user);
    }

    /**
     * 获取关注列表
     * */
    public List<UserInfoVo> getFollow(Integer id) {
        // 获取 ids
        List<Integer> followIds = linkMapper.get(followConfig, id);
        //  获取 users
        List<User> users = list(new QueryWrapper<User>().in("uid", followIds));
        // 返回 userInfoVo
        return users.stream().map(user -> PojoUtil.copy(new UserInfoVo(), user)).collect(Collectors.toList());
    }

}

