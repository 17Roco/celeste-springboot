package com.zsd.celeste.service;

import com.zsd.celeste.entity.DO.ArticleUpdate;
import com.zsd.celeste.entity.PO.Article;
import com.zsd.celeste.util.AutUtil;
import com.zsd.celeste.util.base.service.BaseService;
import com.zsd.celeste.util.result.Result;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * (Article)表服务接口
 *
 * @author zsd
 * @since 2024-06-07 00:46:33
 */
public interface ArticleService extends BaseService<Article> {

    boolean like(Integer aid, Integer uid, boolean b);
    boolean addTag(Integer aid, String tag);
    boolean delTag(Integer aid, String tag);
    boolean addTags(Integer aid, List<String> tags);
    boolean delTags(Integer aid, List<String> tags);


    default boolean updateTags(Integer aid, Map<String,Boolean> update){
        if (Objects.isNull(update))return true;
        List<String> add = new ArrayList<>();
        List<String> del = new ArrayList<>();
        update.forEach((key, value) -> {if (value) add.add(key); else del.add(key);});
        return addTags(aid,add) && delTags(aid,del);
    }

    default Integer saveWithTags(ArticleUpdate update){
        Article article = new Article();
        article.setUid(AutUtil.self().getUid());
        article.update(update);
        if (save(article))
            updateTags(article.getAid(), update.getTagUpdate());
        return article.getAid();
    }
    default boolean update(ArticleUpdate update, Integer aid) {
        Article article = needBySelf(aid);
        article.update(update);
        return updateById(article);
    }
}

