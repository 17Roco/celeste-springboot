package com.zsd.celeste.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zsd.celeste.entity.form.ArticleForm;
import com.zsd.celeste.entity.PO.Article;
import com.zsd.celeste.util.AutUtil;
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


    Article saveBySelf(ArticleForm form);
    boolean updateBySelf(Serializable id, ArticleForm form);

    boolean like(Integer aid, boolean b);
    boolean addTag(Integer aid, String tag);
    boolean delTag(Integer aid, String tag);

    Article getArticle(Integer aid);
    IPage<Article> getArticleList(Integer index, Wrapper<Article> wrapper);

    /**
     * 保存文章内容
     * */
    default Integer saveArticle(ArticleForm update){
        Article article = new Article(null,AutUtil.uid(),update);
        if (!save(article))
            throw new RuntimeException("文章保存失败");
        return article.getAid();
    }
    /**
     * 更新文章内容
     * */
    default boolean update(ArticleForm update, Integer aid) {
        Article article = needBySelf(aid);
        article.update(update);
        return updateById(article);
    }
    /**
     * 修改文章封面
     * */
    String updateImg(MultipartFile file,Integer aid);
    /**
     * 删除文章
     * */
    default boolean deleteArticle(Integer aid) {
        Article article = needBySelf(aid);
        return removeById(aid);
    }
}

