package com.zsd.celeste.controller;
import com.zsd.celeste.entity.PO.Article;
import com.zsd.celeste.service.ArticleService;
import com.zsd.celeste.util.AutUtil;
import com.zsd.celeste.util.base.controller.rest.RESTController;
import com.zsd.celeste.util.base.service.BaseService;
import com.zsd.celeste.util.result.Result;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Objects;

/**
 * (Article)表控制层
 *
 * @author zsd
 * @since 2024-06-07 00:46:33
 */
@RestController
@RequestMapping("/article")
public class ArticleController implements RESTController<Article> {
    
    @Autowired
    private ArticleService service;
    @Autowired
    private AutUtil autUtil;

    @Override
    public BaseService<Article> getService() {
        return service;
    }

    /**
     * 增加记录POST
     * @param article 实体类对象
     * @return 结果
     */
//    @PostMapping("/")
//    @PreAuthorize("@autUtil.needLogin()")
//    Result add(@RequestBody Article article){
//        // 设置 uid 为登陆用户，设置 aid 问 null
//        LoginUser self = AutUtil.self();
//        Article art = new Article();
//        art.setUid(self.getUser().getUid());
//        art.setAid(null);
//        art.setCreateTime(new Date());
//        art.setUpdateTime(new Date());
//        art.setTitle(article.getTitle());
//        art.setContext(article.getContext());
//        boolean save = service.save(art);
//        return Result.judge(save);
//    }

    /**
     * 根据id删除
     * @param aid 删除id
     * @return 结果

    @DeleteMapping("/{aid}")
    @PreAuthorize("@autUtil.needLogin()")
    Result delete(@PathVariable Integer aid){
        LoginUser self = AutUtil.self();
        Article art = service.getById(aid);
        if (Objects.isNull(art)){
            throw new RuntimeException("文章不存在");
        }else if (!Objects.equals(art.getUid(), AutUtil.self().getUser().getUid())){
            throw new RuntimeException("无法删除其他人的文章");
        }
        boolean b = service._del(aid);
        return Result.judge(b);
    }*/

    /**
     * 更新
     * @param article 更新数据
     * @return 结果
    @PutMapping("/content")
    @PreAuthorize("@autUtil.needLogin()")
    Result update(@RequestBody Article article){
        Article art = service.getById(article.getAid());
        if (Objects.isNull(art)){
            throw new RuntimeException("文章不存在");
        }else if (!Objects.equals(art.getUid(), AutUtil.self().getUser().getUid())){
            throw new RuntimeException("无法修改其他人的文章");
        }
        art.setUpdateTime(new Date());
        art.setTitle(article.getTitle());
        art.setContext(article.getContext());
        boolean b = service.updateById(article);
        return Result.judge(b);
    }
     */

    /* ********************************************* */

    /**
     * 增加标签
     * @param tag 更新标签
     * @return 结果
     */
//    @PutMapping("/tag/{aid}/{tag}")
//    @PreAuthorize("@autUtil.needLogin()")
//    Result update(@PathVariable String tag,@PathVariable Integer aid){
//        Article art = service.getById(aid);
//        if (Objects.isNull(art)){
//            throw new RuntimeException("文章不存在");
//        }else if (!Objects.equals(art.getUid(), AutUtil.self().getUser().getUid())){
//            throw new RuntimeException("无法修改其他人的文章");
//        }
//
//        return Result.judge(service.addArticleTag(aid,tag));
//    }

    /**
     * 删除标签
     * @param tag 更新标签
     * @return 结果
     */
//    @DeleteMapping("/tag/{aid}/{tag}")
//    @PreAuthorize("@autUtil.needLogin()")
//    Result delete(@PathVariable String tag,@PathVariable Integer aid){
//        Article art = service.getById(aid);
//        if (Objects.isNull(art)){
//            throw new RuntimeException("文章不存在");
//        }else if (!Objects.equals(art.getUid(), AutUtil.self().getUser().getUid())){
//            throw new RuntimeException("无法修改其他人的文章");
//        }
//
//        return Result.judge(service.delArticleTag(aid,tag));
//    }


}

