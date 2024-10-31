package com.zsd.celeste.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zsd.celeste.entity.PO.Comment;
import com.zsd.celeste.util.base.service.BasePojoService;

public interface CommentService extends BasePojoService<Comment> {


    /**
     * 根据aid获取评论
     * */
    default IPage<Comment> getArticleComment(int aid, int index) {
        return getComment(aid,1,index);
    }

    /**
     * 获取评论
     * */
    IPage<Comment> getComment(int pid, int type, int index);
    default IPage<Comment> getChildrenComment(int pid,int index){
        return getComment(pid,index,0);
    }
    /**
     * 添加评论
     */
    boolean addComment(Comment c,int uid,int type);
    default boolean addChildComment(Comment c,int uid){
        return addComment(c,uid,0);
    }
    /**
     * 删除评论 + 删除子评论
     */
    default boolean deleteComment(int cid){
        return removeById(cid) && remove(new QueryWrapper<Comment>().eq("type",0).eq("pid", cid));
    }
    /**
     * 点赞评论
     * */
    boolean like(int cid,int uid,boolean like);
}