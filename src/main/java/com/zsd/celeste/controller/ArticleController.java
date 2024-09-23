package com.zsd.celeste.controller;
import com.zsd.celeste.entity.DO.ArticleUpdate;
import com.zsd.celeste.entity.PO.Article;
import com.zsd.celeste.service.ArticleService;
import com.zsd.celeste.util.AutUtil;
import com.zsd.celeste.util.base.controller.BaseListPageController;
import com.zsd.celeste.util.base.controller.rest.BaseDeleteByIdController;
import com.zsd.celeste.util.base.controller.rest.BaseGetByIdController;
import com.zsd.celeste.util.base.controller.rest.RESTController;
import com.zsd.celeste.util.base.service.BaseService;
import com.zsd.celeste.util.result.Result;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Objects;

/**
 * (Article)表控制层
 *
 * @author zsd
 * @since 2024-06-07 00:46:33
 */
@RestController
@RequestMapping("/article")
public class ArticleController implements BaseGetByIdController<Article>, BaseDeleteByIdController<Article>, BaseListPageController<Article> {
    
    @Autowired
    private ArticleService service;
    @Autowired
    private AutUtil autUtil;

    @Override
    public BaseService<Article> getService() {
        return service;
    }



    @PreAuthorize("@autUtil.needLogin()")
    @PostMapping("/")
    Result add(@RequestBody ArticleUpdate article) {
//        判断内容是否为空
        if (!StringUtils.hasText(article.getTitle()))
            throw new RuntimeException("标题不能为空");
        if (!StringUtils.hasText(article.getContext()))
            throw new RuntimeException("正文不能为空");
//        保存
        Article a = new Article();
        a.setAid(null);
        a.setUid(autUtil.getLoginUser().getUid());
        a.setTitle(article.getTitle());
        a.setContext(article.getContext());
        a.setUpdateTime(new Date());
        boolean b = service.save(a);
//        todo 更新标签
        if (b){

        }
        return Result.judge(b);
    }

    @PreAuthorize("@autUtil.needLogin()")
    @PutMapping("/")
    Result update(@RequestBody ArticleUpdate article) {
        if (Objects.isNull(article.getAid()))
            throw new RuntimeException("缺乏信息");
        if (!StringUtils.hasText(article.getTitle()))
            throw new RuntimeException("标题不能为空");
        if (!StringUtils.hasText(article.getContext()))
            throw new RuntimeException("正文不能为空");
        // 更新
        Article a = new Article();
        a.setAid(null);
        a.setUid(autUtil.getLoginUser().getUid());
        a.setTitle(article.getTitle());
        a.setContext(article.getContext());
        a.setUpdateTime(new Date());
        boolean b = service.updateById(a);
        return Result.judge(b);
    }
}

