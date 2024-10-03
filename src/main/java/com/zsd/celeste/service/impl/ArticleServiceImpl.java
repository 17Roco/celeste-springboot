package com.zsd.celeste.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsd.celeste.entity.PO.Tag;
import com.zsd.celeste.mapper.ArticleMapper;
import com.zsd.celeste.entity.PO.Article;
import com.zsd.celeste.service.ArticleService;
import com.zsd.celeste.service.TagService;
import com.zsd.celeste.service.UserService;
import com.zsd.celeste.util.link.LinkConfig;
import com.zsd.celeste.util.link.LinkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private UserService userService;
    @Autowired
    private TagService tagService;
    final private LinkConfig likeConfig = new LinkConfig("link_article_like","aid","uid");
    final private LinkConfig tagConfig = new LinkConfig("link_article_tag","aid","tid");






    @Override
    public boolean like(Integer aid, Integer uid, boolean b) {
        Article article = needById(aid);
        article.setLikee(article.getLikee() + (b?1:-1));
        return b?
                linkMapper.addLink(likeConfig,aid,uid) && updateById(article)
                :
                linkMapper.delLink(likeConfig,aid,uid) && updateById(article);
    }

    @Override
    public boolean addTags(Integer aid, List<String> tags) {
        return linkMapper.addLink(tagConfig, aid, tagService.getTagByTitles(tags).stream().map(Tag::getTid).collect(Collectors.toList()));
    }

    @Override
    public boolean delTags(Integer aid, List<String> tags) {
        return linkMapper.delLink(tagConfig, aid, tagService.getTagByTitles(tags).stream().map(Tag::getTid).collect(Collectors.toList()));
    }









    private Article complete(Article article){
        article.setTags(tagService.getTagsByAid(article.getAid()).stream().map(Tag::getTitle).collect(Collectors.toList()));
        article.setUser(userService.needInfoById(article.getUid()));
        return article;
    }

    @Override
    public Article getById(Serializable id) {
        return complete(super.getById(id));
    }

    @Override
    public IPage<Article> page(int index) {
        IPage<Article> page = ArticleService.super.page(index);
        page.getRecords().forEach(this::complete);
        return page;
    }
}

