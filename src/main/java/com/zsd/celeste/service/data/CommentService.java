package com.zsd.celeste.service.data;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zsd.celeste.entity.PO.Comment;
import com.zsd.celeste.entity.form.CommentForm;
import com.zsd.celeste.enums.CommentType;
import com.zsd.celeste.util.base.BaseService;

public interface CommentService extends BaseService<Comment> {

    /**
     * 获取评论
     * */
    IPage<Comment> getComment(Integer pid, CommentType type, int index);
    /**
     * 添加评论
     */
    Comment addComment(CommentForm form, CommentType type);



    /**
     * 删除评论 + 删除子评论
     */
    default boolean deleteComment(Integer cid){
        // todo 验证是否有权限删除
        boolean b = deleteOne(cid);
        // 删除子评论
        if(b)
            remove(new QueryWrapper<Comment>().eq("type",CommentType.CHILDREN.getValue()).eq("pid", cid));
        return b;
    }

    /**
     * 获取评论数量
     * */
    default Long getCommentCount(Integer pid, CommentType type){
        return count(new QueryWrapper<Comment>().eq("pid", pid).eq("type", type.getValue()));
    }



}