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

    @GetMapping({"/article/{aid}","/article/{aid}/{index}"})
    Result getArticleComment(@PathVariable Integer aid, @PathVariable(required = false) Integer index){
        return DataResult.ok(service.getArticleComment(aid,index == null ? 1 : index));
    }
    @GetMapping({"/children/{pcid}","/children/{pcid}/{index}"})
    Result getArticleChildrenComment(@PathVariable Integer pcid, @PathVariable(required = false) Integer index){
        return DataResult.ok(service.getArticleChildrenComment(pcid,index == null ? 1 : index));
    }

    @PreAuthorize("@autUtil.needLogin()")
    @PostMapping("/article")
    Result comment(@RequestBody Comment comment){
        return Result.judge(service.addComment(comment,AutUtil.self().getUid()));
    }

    @PreAuthorize("@commentServiceImpl.needBySelf(#cid)")
    @DeleteMapping("/article/{cid}")
    Result deleteArticleComment(@PathVariable Integer cid){
        return Result.judge(service.deleteComment(cid));
    }
}
