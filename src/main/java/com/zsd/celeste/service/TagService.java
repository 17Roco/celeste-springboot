package com.zsd.celeste.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zsd.celeste.pojo.Tag;
import com.zsd.celeste.service.base.CBaseService;

import java.util.Objects;

/**
 * (Tag)表服务接口
 *
 * @author zsd
 * @since 2024-06-15 14:58:52
 */
public interface TagService extends CBaseService<Tag> {

    /**
     * 通过 title 获取标签
     * */
    default Tag getTagByTitle(String title){
        return _selectOne(w -> w.eq("title", title));
    }

    /**
     * 增加 tag 数量，不存在则创建
     * */
    default boolean addTagNum(String title){
        Tag tag = getTagByTitle(title);
        if (Objects.isNull(tag)){
            tag = new Tag();
            tag.setTitle(title);
            _insert(tag);
        }
        tag.setNum(tag.getNum()+1);
        return _update(tag);
    }
}

