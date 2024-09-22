package com.zsd.celeste.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsd.celeste.mapper.TagMapper;
import com.zsd.celeste.entity.PO.Tag;
import com.zsd.celeste.service.TagService;
import org.springframework.stereotype.Service;

/**
 * (Tag)表服务实现类
 *
 * @author zsd
 * @since 2024-06-15 14:58:52
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

}

