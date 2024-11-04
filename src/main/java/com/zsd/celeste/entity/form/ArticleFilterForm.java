package com.zsd.celeste.entity.form;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
public class ArticleFilterForm {
    private Integer index;
    private String order;
    private String tag;
    private Date beginTime;
    private Date endTime;
    private Integer uid;


    public Integer getIndex() {
        return index == null ? 0 : index;
    }

    public String getOrder() {
        return order == null ? null : ORDER_MAP.getOrDefault(order, "create_time");
    }

    static final Map<String, String> ORDER_MAP;
    static {
        ORDER_MAP = new HashMap<String, String>();
        ORDER_MAP.put("new", "create_time");
        ORDER_MAP.put("watch", "watch");
        ORDER_MAP.put("like", "likee");
    }
}
