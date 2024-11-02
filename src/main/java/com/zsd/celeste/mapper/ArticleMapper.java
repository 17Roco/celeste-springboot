package com.zsd.celeste.mapper;

import com.zsd.celeste.entity.ArticleFilter;
import com.zsd.celeste.entity.form.FilterForm;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zsd.celeste.entity.PO.Article;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * (Article)表数据库访问层
 *
 * @author zsd
 * @since 2024-06-07 00:46:33
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
    List<Article> filter(FilterForm filter);
}

