package com.zsd.celeste.entity;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zsd.celeste.entity.PO.Article;
import com.zsd.celeste.service.ArticleService;
import com.zsd.celeste.service.TagService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleFilter {
    private Integer index = 1;
    private String order;
    private String tag;
    private Date beginTime;
    private Date endTime;
    private Integer uid;

    private List<Integer> getAidsByTid(TagService service){
        if(Objects.isNull(tag))return null;
        return service.getAidsByTag(tag);
    }

    private String getOrderColum(){
        if (Objects.isNull(order) || order.equals("new"))
            return "update_time";
        else if (order.equals("like"))
            return "likee";
        return order;
    }

    public Integer getIndex() {
        return Objects.isNull(index)?1:index;
    }

    public QueryWrapper<Article> wrapper(TagService service){
        List<Integer> aids = getAidsByTid(service);
        if (aids != null && aids.isEmpty())
            aids.add(-1);
        return new QueryWrapper<Article>()
                .eq(!Objects.isNull(uid),                               "uid",uid)
                .in(!Objects.isNull(aids),                              "aid",aids)
                .ge(!Objects.isNull(beginTime),                         "update_time",beginTime)
                .le(!Objects.isNull(endTime),                           "update_time",endTime)
                .orderByDesc(getOrderColum());
    }
}
