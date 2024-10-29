package com.zsd.celeste.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zsd.celeste.entity.PO.Comment;
import com.zsd.celeste.util.base.service.BaseService;

public interface CommentService extends BaseService<Comment> {

    IPage<Comment> getArticleComment(int aid, int index);
    IPage<Comment> getArticleChildrenComment(int cid, int index);

    boolean addComment(Comment c,int uid);
    boolean deleteComment(int cid);
}
