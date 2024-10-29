package com.zsd.celeste.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsd.celeste.entity.PO.Comment;
import com.zsd.celeste.mapper.CommentMapper;
import com.zsd.celeste.service.CommentService;
import com.zsd.celeste.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private UserService userService;

    private final int MAIN_COMMENT_SIZE = 10;
    private final int CHILDREN_COMMENT_SIZE = 5;

    public IPage<Comment> getArticleComment(int aid, int index) {
        IPage<Comment> list = selectMainComment(aid,index);
        list.getRecords().forEach(c->c.setComments(selectChildrenComment(c.getPcid(),1)));
        list.getRecords().forEach(c->c.setUser(userService.getById(c.getUid())));
        return list;
    }

    public IPage<Comment> getArticleChildrenComment(int cid, int index) {
        return selectChildrenComment(cid,index);
    }

    public boolean addComment(Comment c,int uid) {
        Comment comment = new Comment(null,c.getPcid(),c.getAid(),uid,c.getText());
        return save(comment);
    }

    public boolean deleteComment(int cid) {
        return removeById(cid);
    }


    private IPage<Comment> selectMainComment(int aid,int index) {
        return page(index,
                MAIN_COMMENT_SIZE,
                new QueryWrapper<Comment>().eq("aid", aid).eq("pcid", 0)
        );
    }
    private IPage<Comment> selectChildrenComment(int pcid,int index) {
        return page(
                index,
                CHILDREN_COMMENT_SIZE,
                new QueryWrapper<Comment>().eq("pcid", pcid)
        );
    }
}
