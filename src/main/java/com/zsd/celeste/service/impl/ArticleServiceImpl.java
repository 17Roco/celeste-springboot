package com.zsd.celeste.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zsd.celeste.mapper.ArticleMapper;
import com.zsd.celeste.mapper.LinkMapper;
import com.zsd.celeste.pojo.Article;
import com.zsd.celeste.pojo.ArticleFilterConfig;
import com.zsd.celeste.pojo.Tag;
import com.zsd.celeste.service.ArticleService;
import com.zsd.celeste.service.TagService;
import com.zsd.celeste.service.base.CBaseServiceImpl;
import com.zsd.celeste.util.LinkConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (Article)表服务实现类
 *
 * @author zsd
 * @since 2024-06-07 00:46:33
 */
@Service
public class ArticleServiceImpl extends CBaseServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private TagService tagService;
    private final LinkConfig tidToAid = new LinkConfig("link_aid_tid","tid","aid");
    private final LinkConfig aidToTid = new LinkConfig("link_aid_tid","aid","tid");

    @Override
    public void selectAfter(Article entity) {
        // 自动获取tags
        List<Integer> tids = linkMapper.getLinkField(aidToTid, entity.getAid());
        if (tids.isEmpty()) return;
        List<Tag> tags = tagService._select(w -> w.in("tid", tids));
        entity.setTags(tags);
    }

    @Override
    public boolean insertAfter(Article entity) {
        if (super.insertAfter(entity)) {
//            tagService.addTagNum()
            /*
            *
            *
            *
            *
            *
            *
            *
            *
            *
            *
            * */
            return true;
        }else return false;
    }

    /**
     * 通过 tid 获取 文章
     * */
    @Override
    public IPage<Article> getArticleByTag(Integer tid, int index) {
        List<Integer> aids = linkMapper.getLinkField(tidToAid, tid);
        return  _select(w->w.in("aid",aids),index);
    }

    /**
     * 通过过滤条件获取 文章
     * */
    @Override
    public IPage<Article> getArticleByFilterConfig(ArticleFilterConfig config) {
        return _select(
                w->config.wrapper(w,tagService)
                ,config.getIndex()
        );
    }


}

