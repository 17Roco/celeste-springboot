package com.zsd.celeste.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zsd.celeste.entity.PO.Tag;
import com.zsd.celeste.util.base.service.BaseService;

import java.util.List;

/**
 * (Tag)表服务接口
 *
 * @author zsd
 * @since 2024-06-15 14:58:52
 */
public interface TagService extends BaseService<Tag> {

    default Tag getTagByTitle(String title){
        return getOne(new QueryWrapper<Tag>().eq("title", title));
    }
    default Tag needTagByTitle(String title){
        return need(getTagByTitle(title));
    }

    default List<Tag> getTagByTitles(List<String> titles){
        return list(new QueryWrapper<Tag>().in("title",titles));
    }

    List<Tag> getTagsByAid(Integer aid);

    List<Integer> getAidsByTid(Integer tid);
    default List<Integer> getAidsByTag(String title){
        return getAidsByTid(needTagByTitle(title).getTid());
    }
}

