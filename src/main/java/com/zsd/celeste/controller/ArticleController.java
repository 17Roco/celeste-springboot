package com.zsd.celeste.controller;
import com.zsd.celeste.entity.ArticleFilter;
import com.zsd.celeste.entity.DO.ArticleUpdate;
import com.zsd.celeste.service.ArticleService;
import com.zsd.celeste.service.TagService;
import com.zsd.celeste.util.AutUtil;
import com.zsd.celeste.util.result.DataResult;
import com.zsd.celeste.util.result.Result;
import com.zsd.celeste.util.result.StreamResult;
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
//
    @Autowired
    private ArticleService service;
    @Autowired
    private TagService tagService;

    /**
     * 获取指定文章
     * */
    @GetMapping("/{aid}")
    Result get(@PathVariable Integer aid) {
        return DataResult.ok(service.getArticle(aid));
    }
    /**
     * 过滤文章
     * */
    @GetMapping("/filter")
    Result filter(@RequestParam(required = false) Integer index, @RequestParam(required = false) String order, @RequestParam(required = false) String tag, @RequestParam(required = false) Date beginTime, @RequestParam(required = false) Date endTime, @RequestParam(required = false) Boolean self){
        // 构建 ArticleFilter
        Integer uid = self ? AutUtil.self().getUid() : null;
        ArticleFilter filter = new ArticleFilter(index,order,tag,beginTime,endTime,uid);
        // 过滤并返回
        return DataResult.ok(service.getArticleList(filter.getIndex(),filter.wrapper(tagService)));
    }


    /**
     * 保存内容 、 更新内容、删除文章
     * */
    @PostMapping("/")
    Result save(@RequestBody ArticleUpdate update) {
        return StreamResult.create("aid",service.saveArticle(update));
    }
    @PutMapping("/{aid}")
    Result update(@RequestBody ArticleUpdate update,@PathVariable Integer aid) {
        return Result.judge(service.update(update,aid));
    }
    @PostMapping("/{aid}/img")
    Result updateImg(MultipartFile file,@PathVariable Integer aid) {
        // todo
        return null;
    }
    @DeleteMapping("/{aid}")
    public Result delete(@PathVariable Integer aid) {
        return Result.judge(service.deleteArticle(aid));
    }

    /**
     * 点赞 、 取消点赞
     * */
    Result like(Integer aid, boolean b){
        return Result.judge(service.like(aid, b));
    }
    @PostMapping("/like/{aid}")
    Result like(@PathVariable Integer aid){
        return like(aid,true);
    }
    @PostMapping("/unlike/{aid}")
    Result unlike(@PathVariable Integer aid){
        return like(aid,false);
    }



    /**
     * 添加 、 删除 文章标签
     * */
    Result updateTag(Integer aid, String tag,boolean b){
        return Result.judge(b ? service.addTag(aid,tag) : service.delTag(aid,tag));
    }
    @PostMapping("/add_tag/{aid}/{tag}")
    Result addTag(@PathVariable Integer aid, @PathVariable String tag){
        return updateTag(aid,tag,true);
    }
    @PostMapping("/del_tag/{aid}/{tag}")
    Result delTag(@PathVariable Integer aid, @PathVariable String tag){
        return updateTag(aid,tag,false);
    }
}

