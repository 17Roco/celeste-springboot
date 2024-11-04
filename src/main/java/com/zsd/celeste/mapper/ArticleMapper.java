package com.zsd.celeste.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsd.celeste.entity.form.ArticleFilterForm;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zsd.celeste.entity.PO.Article;


/**
 * (Article)表数据库访问层
 *
 * @author zsd
 * @since 2024-06-07 00:46:33
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
    Page<Article> filter(IPage<Article> page, ArticleFilterForm filter);
}

