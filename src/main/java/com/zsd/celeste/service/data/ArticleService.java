package com.zsd.celeste.service.data;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zsd.celeste.entity.PO.Article;
import com.zsd.celeste.entity.form.ArticleFilterForm;
import com.zsd.celeste.util.base.BaseService;
import org.springframework.web.multipart.MultipartFile;

/**
 * (Article)表服务接口
 *
 * @author zsd
 * @since 2024-06-07 00:46:33
 */
public interface ArticleService extends BaseService<Article> {


    IPage<Article> getArticleList(ArticleFilterForm form);
    /**
     * 修改文章封面
     * */
    String updateImg(Integer aid,MultipartFile file);
}

