package com.zsd.celeste.controller;


import com.zsd.celeste.entity.PO.Comment;
import com.zsd.celeste.service.CommentService;
import com.zsd.celeste.util.AutUtil;
import com.zsd.celeste.util.result.DataResult;
import com.zsd.celeste.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService service;

    /**
     * 获取文章的评论
     * */
    @GetMapping({"/article/{aid}","/article/{aid}/{index}"})
    Result getArticleComment(@PathVariable Integer aid, @PathVariable(required = false) Integer index){
        return DataResult.ok(service.getArticleComment(aid,index == null ? 1 : index));
    }

    /**
     * 获取评论的子评论
     * */
    @GetMapping({"/children/{cid}","/children/{cid}/{index}"})
    Result getArticleChildrenComment(@PathVariable Integer cid, @PathVariable(required = false) Integer index){
        return DataResult.ok(service.getChildrenComment(cid,index == null ? 1 : index));
    }

    /**
     * 删除评论
     * */
    @DeleteMapping("/{cid}")
    Result deleteArticleComment(@PathVariable Integer cid){
        service.needBySelf(cid);
        return Result.judge(service.deleteComment(cid));
    }


    /**
     * 在文章下评论
     * */
    @PreAuthorize("@autUtil.needLogin()")
    @PostMapping("/article/{aid}")
    Result comment(@RequestBody Comment comment, @PathVariable Integer aid){
        comment.setPid(aid);
        return Result.judge(service.addComment(comment,AutUtil.self().getUid(),1));
    }

    /**
     * 点赞、取消点赞
     * */
    @PostMapping("like/{cid}")
    public Result like(@PathVariable Integer cid){
        return Result.judge(service.like(cid,AutUtil.uid(),true));
    }
    @PostMapping("unlike/{cid}")
    public Result unlike(@PathVariable Integer cid){
        return Result.judge(service.like(cid,AutUtil.uid(),false));
    }
}
