package com.zsd.celeste.controller;
import com.zsd.celeste.pojo.Article;
import com.zsd.celeste.pojo.ArticleFilterConfig;
import com.zsd.celeste.service.ArticleService;
import com.zsd.celeste.util.Result;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * (Article)表控制层
 *
 * @author zsd
 * @since 2024-06-07 00:46:33
 */
@RestController
@RequestMapping("/blog/article")
public class ArticleController {
    
    @Autowired
    private ArticleService service;

    /**
     * 查询
     * @return 查询结果
     */
    @GetMapping("/list")
    Result getPage(){
        return getPage(1);
    }
    @GetMapping("/list/{index}")
    Result getPage(@PathVariable() Integer index){
        return Result.page(service.page(index));
    }

    /**
     * 根据id查询GET
     * @param id 查询id
     * @return 查询结果
     */
    @GetMapping("/{id}")
    Result getById(@PathVariable Integer id){
        return Result.notNull(service.getById(id));
    }

    /**
     * 增加记录POST
     * @param article 实体类对象
     * @return 结果
     */
    @PostMapping("/")
    Result add(@RequestBody Article article){
        article.setAid(null);
        boolean save = service.save(article);
        return Result.judge(save);
    }

    /**
     * 根据id删除
     * @param id 删除id
     * @return 结果
     */
    @DeleteMapping("/{id}")
    Result delete(@PathVariable Integer id){
        boolean b = service.removeById(id);
        return Result.judge(b);
    }

    /**
     * 更新
     * @param article 更新数据
     * @return 结果
     */
    @PutMapping("/content")
    Result update(@RequestBody Article article){
        boolean b = service.updateById(article);
        return Result.judge(b);
    }

    /* ********************************************* */

    /**
     * 增加标签
     * @param tag 更新标签
     * @return 结果
     */
    @PutMapping("/tag/{aid}/{tag}")
    Result update(@PathVariable String tag,@PathVariable Integer aid){
        return Result.judge(service.addArticleTag(aid,tag));
    }

    /**
     * 删除标签
     * @param tag 更新标签
     * @return 结果
     */
    @DeleteMapping("/tag/{aid}/{tag}")
    Result delete(@PathVariable String tag,@PathVariable Integer aid){
        return Result.judge(service.delArticleTag(aid,tag));
    }




    /* *********************************** */

    /**
     * 根据uid获取文章
     * @param uid index
     * @return 结果
     * */
    @GetMapping("/user/{uid}")
    Result getArticleByUser(@PathVariable Integer uid){
        return getArticleByUser(uid, 1);
    }
    @GetMapping("/user/{uid}/{index}")
    Result getArticleByUser(@PathVariable Integer uid,@PathVariable Integer index){
        return Result.page(service.getArticleByUser(uid,index));
    }

    /**
     * 根据条件过滤文章
     * */
    @GetMapping("/filter")
    Result filter(ArticleFilterConfig config){
        System.out.println(config);
        return Result.page(service.getArticleByFilterConfig(config));
    }
}

