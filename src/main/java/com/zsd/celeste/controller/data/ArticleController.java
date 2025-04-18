package com.zsd.celeste.controller.data;
import com.zsd.celeste.entity.PO.Article;
import com.zsd.celeste.entity.form.ArticleForm;
import com.zsd.celeste.entity.form.ArticleFilterForm;
import com.zsd.celeste.service.data.ArticleService;
import com.zsd.celeste.util.result.Result;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * (Article)表控制层
 *
 * @author zsd
 * @since 2024-06-07 00:46:33
 */
@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService service;

    /**
     * 获取指定文章
     * */
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
    Result save(@RequestBody ArticleForm form) {
        // 保存文章
        Article article = service.create(new Article().update(form));
        // 返回文章id
        return Result.map(map -> map.put("aid", article.getAid()));
    }

    /**
     * 更新内容 （标题、内容）
     * */
    @PutMapping("/context/{aid}")
    Result update(@PathVariable Integer aid,@RequestBody ArticleForm form) {
        return Result.judge(service.change(aid, a->a.update(form)));
    }

    /**
     * 更新封面，并返回新的封面地址
     * */
    @PutMapping("/img/{aid}")
    Result updateImg(@PathVariable Integer aid,MultipartFile file) {
        return Result.map(map -> map.put("img", service.updateImg(aid,file)));
    }

    /**
     * 删除文章
     * */
    @DeleteMapping("/{aid}")
    public Result delete(@PathVariable Integer aid) {
        return Result.judge(service.deleteOne(aid));
    }


}

