package com.zsd.celeste.controller.data;
import com.zsd.celeste.entity.PO.Article;
import com.zsd.celeste.entity.PO.User;
import com.zsd.celeste.entity.form.ArticleForm;
import com.zsd.celeste.entity.form.ArticleFilterForm;
import com.zsd.celeste.service.data.ArticleService;
import com.zsd.celeste.util.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Objects;

/**
 * (Article)表控制层
 *
 * @author zsd
 * @since 2024-06-07 00:46:33
 */
@Tag(name = "文章")
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService service;

    /**
     * 获取指定文章
     * */
    @Operation(summary = "获取指定文章",description = "根据文章id获取指定文章")
    @GetMapping("/{aid}")
    Result get(@PathVariable Integer aid) {
        return Result.ok(service.needById(aid));
    }

    /**
     * 过滤文章
     * */
    @GetMapping("/filter")
    Result filter(@RequestParam(required = false) Integer index, @RequestParam(required = false) String order, @RequestParam(required = false) String tag, @RequestParam(required = false) Date beginTime, @RequestParam(required = false) Date endTime, @RequestParam(required = false) Integer uid,@RequestParam(required = false) Boolean self){
        // 构造过滤表单
        ArticleFilterForm filterForm = new ArticleFilterForm(index, order, tag, beginTime, endTime,uid,self);
        // 获取文章列表
        return Result.ok(service.getArticleList(filterForm));
    }


    /**
     * 保存内容
     * */
    @PostMapping("/context")
    @PreAuthorize("@auth.needUser(#user)")
    Result save(@RequestBody ArticleForm form, User user) {
        // 创建文章
        Article article = new Article().update(form);
        article.setUid(user.getUid());
        // 保存文章
        service.create(article);
        // 返回文章id
        return Result.map(map -> map.put("aid", article.getAid()));
    }

    /**
     * 更新内容 （标题、内容）
     * */
    @PutMapping("/context/{aid}")
    @PreAuthorize("@auth.needUser(#user)")
    Result update(@PathVariable Integer aid,@RequestBody ArticleForm form,User user) {
        return Result.judge(
                // 修改文章
                service.change(aid,
                        a -> a.update(form),
                        a -> {
                            // 验证权限
                            if(!Objects.equals(user.getUid(), a.getUid()))
                                throw new RuntimeException("无权限修改");
                        }
    )
        );
    }

    /**
     * 更新封面，并返回新的封面地址
     * */
    @PutMapping("/img/{aid}")
    @PreAuthorize("@auth.needUser(#user)")
    Result updateImg(@PathVariable Integer aid,MultipartFile file,User user) {
        return Result.map(map -> map.put("img", service.updateImg(aid,file,
                a -> {
                    // 验证权限
                    if(!Objects.equals(user.getUid(), a.getUid()))
                        throw new RuntimeException("无权限修改");
                })));
    }

    /**
     * 删除文章
     * */
    @DeleteMapping("/{aid}")
    @PreAuthorize("@auth.needUser(#user)")
    public Result delete(@PathVariable Integer aid,User user) {
        return Result.judge(service.deleteOne(aid,
                a -> {
                    // 验证权限
                    if(!Objects.equals(user.getUid(), a.getUid()))
                        throw new RuntimeException("无权限修改");
                }));
    }


}

