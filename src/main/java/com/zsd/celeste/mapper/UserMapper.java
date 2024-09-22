package com.zsd.celeste.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zsd.celeste.entity.PO.User;

/**
 * (User)表数据库访问层
 *
 * @author zsd
 * @since 2024-06-07 00:43:07
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {


}

