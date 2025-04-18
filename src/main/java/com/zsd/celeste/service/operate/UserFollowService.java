package com.zsd.celeste.service.operate;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zsd.celeste.entity.PO.User;

public interface UserFollowService{


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
