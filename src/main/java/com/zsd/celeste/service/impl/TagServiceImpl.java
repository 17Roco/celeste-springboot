package com.zsd.celeste.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsd.celeste.mapper.TagMapper;
import com.zsd.celeste.pojo.Tag;
import com.zsd.celeste.service.TagService;
import com.zsd.celeste.service.base.CBaseServiceImpl;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * (Tag)表服务实现类
 *
 * @author zsd
 * @since 2024-06-15 14:58:52
 */
@Service
public class TagServiceImpl extends CBaseServiceImpl<TagMapper, Tag> implements TagService {

}

