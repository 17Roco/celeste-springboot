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
import java.util.Objects;

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
        if (Objects.isNull(entity))return;
        // 自动获取tags
        List<Integer> tids = linkMapper.getLinkField(aidToTid, entity.getAid());
        if (tids.isEmpty()) return;
        List<Tag> tags = tagService._select(w -> w.in("tid", tids));
        entity.setTags(tags);
    }

    @Override
    public boolean insertAfter(Article entity) {
        if (super.insertAfter(entity)) {
            long count = entity.getTags().stream().filter(t -> linkMapper.addLink(aidToTid, entity.getAid(), t.getTid()) == 1).count();
//            if (count != entity.getTags().size())
//                throw new RuntimeException("标签添加异常");
            return true;
        }else return false;
    }

    @Override
    public boolean delAfter(Article entity) {
        if (super.delAfter(entity)) {
            entity.getTags().forEach(t -> linkMapper.addLink(aidToTid, entity.getAid(), t.getTid()));
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

    @Override
    public Boolean addArticleTag(Integer aid,String title) {
        Article article = _selectOne(w -> w.eq("aid", aid));
        Tag tag = tagService.getTagByTitle(title);
        if (Objects.isNull(article))
            throw new RuntimeException("文章不存在");
        if (Objects.isNull(tag))
            throw new RuntimeException("标签不存在");
        if (linkMapper.addLink(aidToTid, aid, tag.getTid()) !=0){
            tagService.addTagNum(title);
            return true;
        }
        return false;
    }

    @Override
    public Boolean delArticleTag(Integer aid,String title) {
        Article article = _selectOne(w -> w.eq("aid", aid));
        Tag tag = tagService.getTagByTitle(title);
        if (Objects.isNull(article))
            throw new RuntimeException("文章不存在");
        if (Objects.isNull(tag))
            throw new RuntimeException("标签不存在");
        if (linkMapper.delLink(aidToTid, aid, tag.getTid()) !=0){
            tagService.defTagNum(title);
            return true;
        }
        return false;
    }
}

