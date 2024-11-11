package com.zsd.celeste.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zsd.celeste.entity.Like;
import com.zsd.celeste.enums.LikeType;
import com.zsd.celeste.util.AutUtil;

public interface LikeService extends IService<Like> {

    /**
     * 获取用户对某个对象的点赞状态
     * @param uid 用户id
     * @param objId 对象id (如：帖子id、评论id)
     * @param type 点赞类型
     * @return true：已点赞；false：未点赞
     * */
    default boolean isLike(Integer uid, Integer objId, LikeType type){
        return count(new QueryWrapper<Like>().eq("uid", uid).eq("obj_id", objId).eq("type", type.getType())) > 0;
    }


    /**
     * 点赞
     * @param uid 用户id
     * @param objId 对象id (如：帖子id、评论id)
     * @param type 点赞类型
     * @return true：点赞成功；false：点赞失败
     * */
    default boolean like(Integer uid, Integer objId, LikeType type){
        boolean like = isLike(uid, objId, type);
        if(!like) {
            return save(new Like(uid, objId, type.getType()));
        }
        return false;
    }


    /**
     * 取消点赞
     * @param uid 用户id
     * @param objId 对象id (如：帖子id、评论id)
     * @param type 点赞类型
     * @return true：取消点赞成功；false：取消点赞失败
     * */
    default boolean unlike(Integer uid, Integer objId, LikeType type){
        boolean like = isLike(uid, objId, type);
        if(like) {
            return remove(new QueryWrapper<Like>().eq("uid", uid).eq("obj_id", objId).eq("type", type.getType()));
        }
        return false;
    }

    default boolean isLike(Integer objId, LikeType type){
        return isLike(AutUtil.uid(),objId,type);
    }
    default boolean like(Integer objId, LikeType type){
        return like(AutUtil.uid(),objId,type);
    }
    default boolean unlike(Integer objId, LikeType type){
        return unlike(AutUtil.uid(),objId,type);
    }
}
