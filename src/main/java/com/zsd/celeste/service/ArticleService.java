package com.zsd.celeste.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zsd.celeste.pojo.Article;
import com.zsd.celeste.service.base.CBaseService;
import java.util.Date;

/**
 * (Article)表服务接口
 *
 * @author zsd
 * @since 2024-06-07 00:46:33
 */
public interface ArticleService extends CBaseService<Article> {
    enum ArticleTimeRange{ total,mouth,week }

    IPage<Article> heightLike(Date beginTime,Date endTime, int index);
    IPage<Article> hot(Date beginTime,Date endTime,int index);
}

