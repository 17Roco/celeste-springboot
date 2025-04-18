package com.zsd.celeste.service.operate.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zsd.celeste.entity.Like;
import com.zsd.celeste.mapper.LikeMapper;
import com.zsd.celeste.service.operate.LikeService;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl extends ServiceImpl<LikeMapper, Like> implements LikeService {
}
