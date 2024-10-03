package com.zsd.celeste.service;

import com.zsd.celeste.entity.PO.Article;
import com.zsd.celeste.util.base.service.BaseService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * (Article)表服务接口
 *
 * @author zsd
 * @since 2024-06-07 00:46:33
 */
public interface ArticleService extends BaseService<Article> {

    boolean like(Integer aid, Integer uid, boolean b);


    boolean addTags(Integer aid, List<String> tags);
    boolean delTags(Integer aid, List<String> tags);
    default boolean updateTags(Integer aid, Map<String,Boolean> update){
        List<String> add = new ArrayList<>();
        List<String> del = new ArrayList<>();
        update.forEach((key, value) -> {if (value) add.add(key); else del.add(key);});
        return addTags(aid,add) && delTags(aid,del);
    }
}

