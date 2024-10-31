package com.zsd.celeste.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsd.celeste.entity.PO.Comment;
import com.zsd.celeste.mapper.CommentMapper;
import com.zsd.celeste.service.CommentService;
import org.springframework.stereotype.Service;


@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    private final int COMMENT_SIZE = 10;


    /**
     * 获取评论
     * */
    public IPage<Comment> getComment(int pid,int type,int index) {
        return page(index,
                COMMENT_SIZE,
                new QueryWrapper<Comment>().eq("type", type).eq("pid", pid)
        );
    }
    /**
     * 添加评论
     * */
    public boolean addComment(Comment c,int uid,int type) {
        Comment comment = new Comment(null,c.getPid(),type,uid,c.getText());
        return save(comment);
    }

    /**
     * 点赞评论
     * */
    public boolean like(int cid,int uid,boolean like) {
        return true;// todo
    }
}
