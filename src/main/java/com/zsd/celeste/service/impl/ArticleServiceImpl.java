package com.zsd.celeste.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsd.celeste.entity.PO.Tag;
import com.zsd.celeste.mapper.ArticleMapper;
import com.zsd.celeste.entity.PO.Article;
import com.zsd.celeste.service.ArticleService;
import com.zsd.celeste.service.TagService;
import com.zsd.celeste.service.UserService;
import com.zsd.celeste.util.AutUtil;
import com.zsd.celeste.util.link.LinkConfig;
import com.zsd.celeste.util.link.LinkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public String getResourceMsg() {
        return "文章不存在";
    }

    final private LinkConfig likeConfig = new LinkConfig("link_article_like","aid","uid");
    final private LinkConfig tagConfig = new LinkConfig("link_article_tag","aid","tid");

    private Article complete(Article article){
        // 获取标签
        List<Tag> tags = tagService.getTagsByAid(article.getAid());
        // 获取标签标题
        article.setTags(tags.stream().map(Tag::getTitle).collect(Collectors.toList()));
        // 获取用户
        article.setUser(userService.getUserInfo(article.getUid()));
        return article;
    }


    /*
    * 点赞
    * */
    public boolean like(Integer aid, boolean b) {
        // 获取文章，like +/-=1
        Article article = needById(aid);
        article.changeLike((b?1:-1));
        return b?
                linkMapper.addLink(likeConfig,aid, AutUtil.uid()) && updateById(article)
                :
                linkMapper.delLink(likeConfig,aid,AutUtil.uid()) && updateById(article);
    }

    /*
    * addTag  / delTag
    * addTags / delTags
    * */
    public boolean addTag(Integer aid, String tag) {
        needBySelf(aid);
        tagService.needTagByTitle(tag);
        return linkMapper.addLink(tagConfig,aid,tagService.needTagByTitle(tag).getTid());
    }

    public boolean delTag(Integer aid, String tag) {
        needBySelf(aid);
        tagService.needTagByTitle(tag);
        return linkMapper.delLink(tagConfig,aid,tagService.needTagByTitle(tag).getTid());
    }

    /**
     * getArticle
     * getArticleList
     * */
    public Article getArticle(Integer aid) {
        return complete(getById(aid));
    }
    public IPage<Article> getArticleList(Integer index, Wrapper<Article> wrapper) {
        IPage<Article> page = page(index,wrapper);
        page.getRecords().forEach(this::complete);
        return page;
    }

}

