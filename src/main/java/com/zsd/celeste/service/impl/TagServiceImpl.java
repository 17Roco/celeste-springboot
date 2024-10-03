package com.zsd.celeste.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsd.celeste.mapper.TagMapper;
import com.zsd.celeste.entity.PO.Tag;
import com.zsd.celeste.service.TagService;
import com.zsd.celeste.util.link.LinkConfig;
import com.zsd.celeste.util.link.LinkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (Tag)表服务实现类
 *
 * @author zsd
 * @since 2024-06-15 14:58:52
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Autowired
    private LinkMapper linkMapper;
    final private LinkConfig tagConfig = new LinkConfig("link_article_tag","aid","tid");

    @Override
    public List<Tag> getTagsByAid(Integer aid) {
        List<Integer> tids = linkMapper.get(tagConfig, aid);
        return list(new QueryWrapper<Tag>().in("tid", tids));
    }


    @Override
    public List<Integer> getAidsByTid(Integer tid) {
        return linkMapper.get(tagConfig.toRe(), tid);
    }

}


