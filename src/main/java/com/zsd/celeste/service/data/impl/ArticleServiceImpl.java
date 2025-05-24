package com.zsd.celeste.service.data.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsd.celeste.entity.PO.Tag;
import com.zsd.celeste.entity.form.ArticleFilterForm;
import com.zsd.celeste.enums.ResourceNameSpace;
import com.zsd.celeste.mapper.ArticleMapper;
import com.zsd.celeste.entity.PO.Article;
import com.zsd.celeste.service.data.ArticleService;
import com.zsd.celeste.service.common.FileResourceService;
import com.zsd.celeste.service.data.TagService;
import com.zsd.celeste.service.data.UserService;
import com.zsd.celeste.util.base.EditPojoInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    private UserService userService;
    @Autowired
    private TagService tagService;
    @Autowired
    private FileResourceService resourceService;


    /**
     * 补全
     * */
    private Article complete(Article article) {
        // 获取标签
        List<Tag> tags = tagService.getTagsByAid(article.getAid());
        article.setTags(tags.stream().map(Tag::getTitle).collect(Collectors.toList()));
        // 获取用户
        article.setUser(userService.getById(article.getUid()));
        // 返回
        return article;
    }


    /**
     * 获取文章
     * */
    public Article needById(Serializable id) {
        // 获取文章
        Article article = ArticleService.super.needById(id);
        // 补全
        return complete(article);
    }

    /**
    * 获取文章列表
    * */
    public IPage<Article> getArticleList(ArticleFilterForm form) {
        // 获取分页
        Page<Article> page = Page.of(form.getIndex(), getSize());
        // 过滤
        Page<Article> filter = getBaseMapper().filter(page, form);
        // 补全
        filter.getRecords().forEach(this::complete);
        // 返回
        return filter;
    }

    /**
     * 修改封面
     * */
    public String updateImg(Integer aid, MultipartFile file, EditPojoInterface<Article> before) {
        // 获取文章
        Article article = needById(aid);
        // before
        before.edit(article);
        // 保存图片
        String img = resourceService.saveResource(file, ResourceNameSpace.IMAGE_ARTICLE);
        // 修改并保存
        article.setImg(img);
        updateById(article);
        // 返回图片路径
        return img;
    }

}

