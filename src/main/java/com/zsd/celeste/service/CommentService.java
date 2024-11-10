package com.zsd.celeste.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zsd.celeste.entity.PO.Comment;
import com.zsd.celeste.entity.form.CommentForm;
import com.zsd.celeste.enums.CommentType;
import com.zsd.celeste.util.base.BasePojoService;

public interface CommentService extends BasePojoService<Comment> {

    /**
     * 获取评论
     * */
    IPage<Comment> getComment(int pid, CommentType type, int index);
    /**
     * 添加评论
     */
    boolean addComment(CommentForm form, CommentType type);
    /**
     * 删除评论 + 删除子评论
     */
    default boolean deleteComment(int cid){
        return removeBySelf(cid) && remove(new QueryWrapper<Comment>().eq("type",CommentType.ARTICLE.getValue()).eq("pid", cid));
    }


    /**
     * 获取文章评论
     * 获取子评论
     * */
    default IPage<Comment> getChildrenComment(int pid,int index){
        return getComment(pid,CommentType.CHILDREN,index);
    }
    default IPage<Comment> getArticleComment(int aid, int index) {
        return getComment(aid,CommentType.ARTICLE,index);
    }

    /**
     * 点赞评论
     * */
    boolean like(int cid,boolean like);
}