package com.zsd.celeste.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zsd.celeste.entity.form.ArticleForm;
import com.zsd.celeste.entity.PO.Article;
import com.zsd.celeste.entity.form.ArticleFilterForm;
import com.zsd.celeste.util.base.service.BasePojoService;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * (Article)表服务接口
 *
 * @author zsd
 * @since 2024-06-07 00:46:33
 */
public interface ArticleService extends BasePojoService<Article> {


    /**
     * 保存文章
     * 更新文章
     * 获取文章列表
     * */
    Article saveBySelf(ArticleForm form);
    boolean updateBySelf(Serializable id, ArticleForm form);
    Article getArticleById(Serializable id);
    IPage<Article> getArticleList(ArticleFilterForm form);

    /**
     * 点赞
     * 添加、删除标签
     * */
    boolean like(Integer aid, boolean b);
    boolean addTag(Integer aid, String tag);
    boolean delTag(Integer aid, String tag);


    /**
     * 修改文章封面
     * */
    String updateImg(MultipartFile file,Integer aid);
}

