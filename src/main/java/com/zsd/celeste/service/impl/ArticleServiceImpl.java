package com.zsd.celeste.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsd.celeste.mapper.ArticleMapper;
import com.zsd.celeste.entity.PO.Article;
import com.zsd.celeste.service.ArticleService;
import com.zsd.celeste.service.TagService;
import com.zsd.celeste.util.link.LinkConfig;
import com.zsd.celeste.util.link.LinkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * (Article)表服务实现类
 *
 * @author zsd
 * @since 2024-06-07 00:46:33
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private LinkMapper linkMapper;

    final private LinkConfig likeConfig = new LinkConfig("link_article_like","aid","uid");

    @Override
    public boolean like(Integer aid, Integer uid, boolean b) {
        Article article = needById(aid);
        article.setLikee(article.getLikee() + (b?1:-1));
        return b?
                linkMapper.addLink(likeConfig,aid,uid) && updateById(article)
                :
                linkMapper.delLink(likeConfig,aid,uid) && updateById(article);
    }
}

