package com.zsd.celeste.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsd.celeste.entity.form.LoginUser;
import com.zsd.celeste.entity.form.UserInfoForm;
import com.zsd.celeste.enums.ResourceNameSpace;
import com.zsd.celeste.mapper.UserMapper;
import com.zsd.celeste.entity.PO.User;
import com.zsd.celeste.service.FileResourceService;
import com.zsd.celeste.service.TokenService;
import com.zsd.celeste.service.UserService;
import com.zsd.celeste.util.AutUtil;
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

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
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
    @Autowired
    private LinkMapper linkMapper;
    final private LinkConfig followConfig = new LinkConfig("link_user_follow","id","uid");








    /**
     * 更新信息,username,sex,birthday,sign
     * */
    public boolean updateBySelf(UserInfoForm form) {
        return UserService.super.updateBySelf(AutUtil.uid(), u->u.update(form));
    }

    /**
     * 修改密码
     * */
    public boolean updatePassword(String username, String oldPassword, String newPassword) {
//        LoginUser auth = auth(username, oldPassword);
//        User user = auth.getUser();
//        user.setPassword(passwordEncoder.encode(newPassword));
//        if (!updateById(user))
//            throw new RuntimeException("修改失败");
//        tokenService.removeUid(user.getUid());
        return true;
    }
    /**
     * 更新头像
     * */
    public String updateImg(MultipartFile file) {
        // 获取用户
        User user = needBySelf(AutUtil.uid());
        // 保存图片
        String img = fileResourceService.saveResource(file, ResourceNameSpace.IMAGE_USER);
        // 修改并保存
        user.setImg(img);
        updateById(user);
        return img;
    }















    /**
     * 关注用户
     * */
    public boolean follow(Integer id, Integer uid,boolean b) {
        User self = needById(id);
        User user = needById(uid);
        self.setFollow(self.getFollow()+(b?1:-1));
        user.setFollowed(self.getFollowed()+(b?1:-1));
        return b ?
                linkMapper.addLink(followConfig,id,uid) && updateById(self) && updateById(user)
                :
                linkMapper.delLink(followConfig,id,uid) && updateById(self) && updateById(user);
    }

    /**
     * 获取关注列表
     * */
    public IPage<User> getFollow(Integer id, Integer index) {
        Page<User> page = Page.of(index, getSize());
        return getBaseMapper().getFollowList(page, id);
    }

    /**
     * 获取被关注列表
     * */
    public IPage<User> getFollowed(Integer id, Integer index) {
        Page<User> page = Page.of(index, getSize());
        return getBaseMapper().getFollowedList(page, id);
    }

    /**
     * 是否关注
    * */
    public boolean isFollow(Integer id, Integer uid) {
        return linkMapper.exits(followConfig, id, uid);
    }
}

