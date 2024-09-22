package com.zsd.celeste.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zsd.celeste.entity.PO.Tag;
import com.zsd.celeste.util.base.service.BaseService;

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
}

