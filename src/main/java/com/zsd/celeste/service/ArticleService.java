package com.zsd.celeste.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zsd.celeste.pojo.Article;
import com.zsd.celeste.pojo.ArticleFilterConfig;
import com.zsd.celeste.service.base.CBaseService;

import java.util.List;
import java.util.function.Function;

/**
 * (Article)表服务接口
 *
 * @author zsd
 * @since 2024-06-07 00:46:33
 */
public interface ArticleService extends CBaseService<Article> {

    /**
     * 通过 uid 获取 文章
     * */
    default IPage<Article> getArticleByUser(Integer uid, int index){
        return _select(w->w.eq("uid",uid),index);
    };

    /**
     * 通过 tid 获取 文章
     * */
    IPage<Article> getArticleByTag(Integer tid, int index);

    /**
     * 通过过滤条件获取 文章
     * */
    IPage<Article> getArticleByFilterConfig(ArticleFilterConfig config);

    /**
     * 增加标签
     * */
    Boolean addArticleTag(Integer aid,String title);
    /**
     * 删除标签
     * */
    Boolean delArticleTag(Integer aid,String title);
}

