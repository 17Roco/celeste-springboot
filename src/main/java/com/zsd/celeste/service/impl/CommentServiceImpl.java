package com.zsd.celeste.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsd.celeste.entity.PO.Comment;
import com.zsd.celeste.entity.PO.User;
import com.zsd.celeste.entity.form.CommentForm;
import com.zsd.celeste.enums.CommentType;
import com.zsd.celeste.enums.LikeType;
import com.zsd.celeste.mapper.CommentMapper;
import com.zsd.celeste.service.CommentService;
import com.zsd.celeste.service.LikeService;
import com.zsd.celeste.service.UserService;
import com.zsd.celeste.util.AutUtil;
import com.zsd.celeste.util.PojoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    private final int COMMENT_SIZE = 10;

    @Autowired
    private LikeService likeService;
    @Autowired
    private UserService userService;


    /**
     * 获取评论
     * */
    public IPage<Comment> getComment(int pid, CommentType type, int index) {
        // 获取评论
        IPage<Comment> comments = page(index, COMMENT_SIZE, new QueryWrapper<Comment>().eq("type", type.getValue()).eq("pid", pid));

        // 获取用户信息、子评论数
        comments.getRecords().forEach(comment -> {
            // 获取评论用户信息
            User user = userService.getById(comment.getUid());
            // 设置用户信息
            comment.setUser(user);

            // 获取子评论数
            Long count = getCommentCount(comment.getCid(), CommentType.CHILDREN);
            // 设置子评论数
            comment.setChildrenCount(Math.toIntExact(count));
        });

        // 如果用户已登录，则设置是否点赞
        AutUtil.login(()->
                comments.getRecords().forEach(comment ->
                        comment.setIsLike(likeService.isLike(comment.getCid(), LikeType.COMMENT))
                )
        );

        return comments;
    }

    /**
     * 添加评论
     * */
    public Comment addComment(CommentForm form, CommentType type) {

        return saveBySelf(form.toComment(type));
    }

    /**
     * 点赞评论
     * */
    public boolean like(int cid,boolean like) {
        return like ?likeService.like(cid, LikeType.COMMENT) : likeService.unlike(cid, LikeType.COMMENT);
    }
}
