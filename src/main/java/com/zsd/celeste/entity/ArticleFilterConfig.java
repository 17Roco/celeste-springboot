package com.zsd.celeste.entity;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zsd.celeste.entity.PO.Article;
import com.zsd.celeste.service.ArticleService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleFilterConfig {
    private Integer index = 1;
    private String order;
    private String tag;
    private Date beginTime;
    private Date endTime;
    private Integer uid;
    private Boolean self = false;

    private List<Integer> getAids(ArticleService service){
        if(Objects.isNull(tag))return null;
        // getTag
//        Tag tag1 = service._selectOne(w -> w.eq("title", getTag()));
//        if (Objects.isNull(tag1))
//            throw new RuntimeException("标签不存在");
//        List<Integer> aids = service.getAidsByTag(tag);
//        if (Objects.isNull(aids) || aids.isEmpty())
//            throw new RuntimeException("标签不存在");
//        return aids;
        return null;
    }

    private String getOrderColum(){
        if (Objects.isNull(order) || order.equals("new"))
            return "update_time";
        else if (order.equals("like"))
            return "likee";
        return order;
    }

    public QueryWrapper<Article> wrapper(QueryWrapper<Article> wrapper, ArticleService service){
        System.out.println(uid);
        return wrapper
                .eq(!Objects.isNull(uid),"uid",uid)
                .in(!Objects.isNull(tag)&&!tag.isEmpty(),"aid",getAids(service))
                .ge(!Objects.isNull(beginTime),"update_time",beginTime)
                .le(!Objects.isNull(endTime),"update_time",endTime)
                .orderByDesc(getOrderColum());
    }
}
