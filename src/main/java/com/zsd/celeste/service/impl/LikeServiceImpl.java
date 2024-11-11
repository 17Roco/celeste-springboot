package com.zsd.celeste.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsd.celeste.entity.Like;
import com.zsd.celeste.mapper.LikeMapper;
import com.zsd.celeste.service.LikeService;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl extends ServiceImpl<LikeMapper, Like> implements LikeService {
}
