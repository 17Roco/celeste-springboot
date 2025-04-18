package com.zsd.celeste.service.operate.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsd.celeste.entity.PO.User;
import com.zsd.celeste.service.operate.UserFollowService;
import org.springframework.stereotype.Service;

@Service
public class UserFollowServiceImpl implements UserFollowService {
    @Override
    public IPage<User> getFollow(Integer id, Integer index) {
        return null;
    }

    @Override
    public IPage<User> getFollowed(Integer id, Integer index) {
        return null;
    }

    @Override
    public boolean follow(Integer id, Integer uid, boolean b) {
        return false;
    }

    @Override
    public boolean isFollow(Integer id, Integer uid) {
        return false;
    }




    /**
     * 关注用户
     * */
//    public boolean follow(Integer id, Integer uid,boolean b) {
//        User self = needById(id);
//        User user = needById(uid);
//        self.setFollow(self.getFollow()+(b?1:-1));
//        user.setFollowed(self.getFollowed()+(b?1:-1));
//        return b ?
//                linkMapper.addLink(followConfig,id,uid) && updateById(self) && updateById(user)
//                :
//                linkMapper.delLink(followConfig,id,uid) && updateById(self) && updateById(user);
//    }
//
//    /**
//     * 获取关注列表
//     * */
//    public IPage<User> getFollow(Integer id, Integer index) {
//        Page<User> page = Page.of(index, getSize());
//        return getBaseMapper().getFollowList(page, id);
//    }
//
//    /**
//     * 获取被关注列表
//     * */
//    public IPage<User> getFollowed(Integer id, Integer index) {
//        Page<User> page = Page.of(index, getSize());
//        return getBaseMapper().getFollowedList(page, id);
//    }
//
//    /**
//     * 是否关注
//     * */
//    public boolean isFollow(Integer id, Integer uid) {
//        return linkMapper.exits(followConfig, id, uid);
//    }
}
