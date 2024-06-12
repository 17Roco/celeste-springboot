package com.zsd.celeste.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zsd.celeste.mapper.ArticleMapper;
import com.zsd.celeste.pojo.Article;
import com.zsd.celeste.service.ArticleService;
import com.zsd.celeste.service.base.CBaseServiceImpl;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Objects;

/**
 * (Article)表服务实现类
 *
 * @author zsd
 * @since 2024-06-07 00:46:33
 */
@Service
public class ArticleServiceImpl extends CBaseServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Override
    public IPage<Article> heightLike(Date beginTime, Date endTime, int index) {
        return select(w -> w
                        .ge(!Objects.isNull(beginTime), "updateTime", beginTime)
                        .le(!Objects.isNull(endTime), "updateTime", endTime)
                        .orderByDesc("likee")
                , index);
    }

    @Override
    public IPage<Article> hot(Date beginTime, Date endTime, int index) {
        return select(w -> w
                        .ge(!Objects.isNull(beginTime), "updateTime", beginTime)
                        .le(!Objects.isNull(endTime), "updateTime", endTime)
                        .orderByDesc("watch")
                , index);
    }
}

