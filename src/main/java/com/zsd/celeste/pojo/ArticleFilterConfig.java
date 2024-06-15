package com.zsd.celeste.pojo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
public class ArticleFilterConfig {
    private Integer index = 1;
    private String order;
    private String tag;
    private Date beginTime;
    private Date endTime;

    private List<Integer> getAids(TagService service){
        if(Objects.isNull(tag))return null;
        // getTag
        Tag tag1 = service._selectOne(w -> w.eq("title", getTag()));
        if (Objects.isNull(tag1))
            throw new RuntimeException("标签不存在");
        return service.getLinkMapper().getAidByTid(tag1.getTid());
    }

    private String getOrderColum(){
        if (Objects.isNull(order) || order.equals("new"))
            return "create_time";
        return order;
    }

    public QueryWrapper<Article> wrapper(QueryWrapper<Article> wrapper, TagService service){
        return wrapper
                .in(!Objects.isNull(tag),"aid",getAids(service))
                .ge(!Objects.isNull(beginTime),"create_time",beginTime)
                .le(!Objects.isNull(endTime),"create_time",endTime)
                .orderByDesc(getOrderColum());
    }
}
