package com.zsd.celeste.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsd.celeste.mapper.ArticleMapper;
import com.zsd.celeste.pojo.Article;
import com.zsd.celeste.service.ArticleService;
import com.zsd.celeste.service.base.CBaseServiceImpl;
import com.zsd.celeste.service.inter.selectWrapperInterface;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * (Article)表服务实现类
 *
 * @author zsd
 * @since 2024-06-07 00:46:33
 */
@Service
public class ArticleServiceImpl extends CBaseServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Override
    public IPage<Article> heightLike(ArticleTimeRange timeRange, int index) {
//        Date date = new Date();
//        if(timeRange == ArticleTimeRange.mouth){
//            date.setHours();
//        }
//        select( w-> w.eq("update_time",).orderByDesc("update_time"));
        return null;
    }

    @Override
    public IPage<Article> hot(ArticleTimeRange timeRange, int index) {
        return null;
    }


    Date getCurrentWeekFirstDay(){
        Calendar c = Calendar.getInstance();
//        c.get(Calendar.W)
        return null;
    }
}

