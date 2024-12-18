package com.zsd.celeste.controller;


import com.zsd.celeste.entity.form.CommentForm;
import com.zsd.celeste.enums.CommentType;
import com.zsd.celeste.service.ArticleService;
import com.zsd.celeste.service.CommentService;
import com.zsd.celeste.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService service;
    @Autowired
    private ArticleService articleService;

    /**
     * 获取文章的评论
     * */
    @GetMapping("/article/{aid}/{index}")
    Result getArticleComment(@PathVariable Integer aid, @PathVariable Integer index){
        return Result.ok(service.getArticleComment(aid, index));
    }

    /**
     * 获取评论的子评论
     * */
    @GetMapping("/reply/{cid}/{index}")
    Result getArticleChildrenComment(@PathVariable Integer cid, @PathVariable Integer index){
        return Result.ok(service.getChildrenComment(cid,index));
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
    @PostMapping("/article")
    Result comment(@RequestBody CommentForm form){
        articleService.needById(form.getPid());
        return Result.ok(service.addComment(form, CommentType.ARTICLE));
    }
    @PostMapping("/reply")
    Result reply(@RequestBody CommentForm form){
        service.needById(form.getPid());
        return Result.ok(service.addComment(form, CommentType.CHILDREN));
    }


    /**
     * 点赞、取消点赞
     * */
    @PostMapping("like/{cid}")
    public Result like(@PathVariable Integer cid){
        return Result.judge(service.like(cid,true));
    }
    @PostMapping("unlike/{cid}")
    public Result unlike(@PathVariable Integer cid){
        return Result.judge(service.like(cid,false));
    }
}
