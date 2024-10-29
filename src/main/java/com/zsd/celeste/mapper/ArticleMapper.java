package com.zsd.celeste.mapper;

import com.zsd.celeste.entity.ArticleFilter;
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

//    @Select("select * from article join link_article_tag lat on article.aid = lat.aid where uid=#filter.uid and tid=#filter.tid")
//    List<Article> filter(ArticleFilter filter);
}

