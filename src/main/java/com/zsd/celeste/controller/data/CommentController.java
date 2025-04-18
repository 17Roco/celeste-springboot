package com.zsd.celeste.controller.data;


import com.zsd.celeste.entity.form.CommentForm;
import com.zsd.celeste.enums.CommentType;
import com.zsd.celeste.service.data.CommentService;
import com.zsd.celeste.util.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService service;

    /**
     * 获取的评论
     * */
    @GetMapping("/{type}/{pid}/{index}")
    Result getComment(@PathVariable String type,@PathVariable Integer pid, @PathVariable Integer index){
        // 获取评论类型
        CommentType t = CommentType.getByName(type);
        if(t == null)
            throw new RuntimeException("评论类型错误");
        // 获取评论
        return Result.ok(service.getComment(pid,t,index));
    }


    /**
     * 删除评论
     * */
    @DeleteMapping("/{cid}")
    Result deleteComment(@PathVariable Integer cid){
        // 验证是否为自己的评论
        // todo 验证是否为自己的评论
        // 删除评论
        return Result.judge(service.deleteComment(cid));
    }


    /**
     * 评论
     * */
    @PostMapping("/{type}/{pid}")
    Result comment(@PathVariable String type,@RequestBody CommentForm form){
        // 获取评论类型
        CommentType t = CommentType.getByName(type);
        if(t == null)
            throw new RuntimeException("评论类型错误");
        // todo 判断父评论是否存在
        //
        // 评论
        return Result.ok(service.addComment(form, t));
    }

}
