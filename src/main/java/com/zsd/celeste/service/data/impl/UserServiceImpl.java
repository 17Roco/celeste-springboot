package com.zsd.celeste.service.data.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsd.celeste.entity.form.UserInfoForm;
import com.zsd.celeste.enums.ResourceNameSpace;
import com.zsd.celeste.mapper.UserMapper;
import com.zsd.celeste.entity.PO.User;
import com.zsd.celeste.service.common.FileResourceService;
import com.zsd.celeste.service.common.TokenService;
import com.zsd.celeste.service.data.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    FileResourceService fileResourceService;
    @Autowired
    PasswordEncoder passwordEncoder;








    /**
     * 更新信息,username,sex,birthday,sign
     * */
    public User updateInfo(Integer uid, UserInfoForm form) {
        return change(uid, u -> u.update(form));
    }

    /**
     * 修改密码
     * */
    public User updatePassword(Integer uid,String newPassword) {
        return change(uid, u -> u.setPassword(passwordEncoder.encode(newPassword)));
    }
    /**
     * 更新头像
     * */
    public String updateImg(Integer uid,MultipartFile file) {
        // 获取用户
        User user = needById(uid);
        // 保存图片
        String img = fileResourceService.saveResource(file, ResourceNameSpace.IMAGE_USER);
        // 修改并保存
        user.setImg(img);
        change(uid, u -> u.setImg(img));
        return img;
    }

}

