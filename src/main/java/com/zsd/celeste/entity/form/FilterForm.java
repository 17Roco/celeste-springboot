package com.zsd.celeste.entity.form;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class FilterForm {
    private Integer index;
    private String order;
    private String tag;
    private Date beginTime;
    private Date endTime;
    private Integer uid;
}
