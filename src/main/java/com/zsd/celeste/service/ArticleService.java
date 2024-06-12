package com.zsd.celeste.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zsd.celeste.pojo.Article;
import com.zsd.celeste.service.base.CBaseService;
import com.zsd.celeste.service.inter.selectWrapperInterface;

import java.util.List;

/**
 * (Article)表服务接口
 *
 * @author zsd
 * @since 2024-06-07 00:46:33
 */
public interface ArticleService extends CBaseService<Article> {
    enum ArticleTimeRange{ total,mouth,week }

    IPage<Article> heightLike(ArticleTimeRange timeRange,int index);
    IPage<Article> hot(ArticleTimeRange timeRange,int index);
}

