package com.zsd.celeste.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zsd.celeste.entity.PO.Tag;

/**
 * (Tag)表数据库访问层
 *
 * @author zsd
 * @since 2024-06-15 14:58:52
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {

}

