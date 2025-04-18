package com.zsd.celeste.service.data.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsd.celeste.entity.PO.Comment;
import com.zsd.celeste.entity.form.CommentForm;
import com.zsd.celeste.enums.CommentType;
import com.zsd.celeste.mapper.CommentMapper;
import com.zsd.celeste.service.data.CommentService;
import com.zsd.celeste.service.data.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    private final int COMMENT_SIZE = 10;

    @Autowired
    private UserService userService;


    /**
     * 获取评论
     * */
    public IPage<Comment> getComment(Integer pid, CommentType type, int index) {
        // 获取评论
        IPage<Comment> comments = page(index, COMMENT_SIZE, new QueryWrapper<Comment>().eq("type", type.getValue()).eq("pid", pid));
        // 获取用户信息、子评论数
        comments.getRecords().forEach(comment -> {
            // 获取评论用户信息
            comment.setUser(userService.getById(comment.getUid()));
            // 获取子评论数
            comment.setChildrenCount(Math.toIntExact(getCommentCount(comment.getCid(), CommentType.CHILDREN)));
        });
        // TODO 如果用户已登录，则设置是否点赞
        return comments;
    }

    /**
     * 添加评论
     * */
    public Comment addComment(CommentForm form, CommentType type) {
        // 保存评论
        return create(form.toComment(type));
    }

}
