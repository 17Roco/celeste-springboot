package com.zsd.celeste.entity.PO;


import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * (Tag)实体类
 *
 * @author zsd
 * @since 2024-06-15 14:58:52
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("tag")
public class Tag{
    @JsonIgnore
    @TableId("tid")
    private Integer tid;

    @TableField("title")
    private String title;
    @TableField("info")
    private String info;
    @TableField("num")
    private Integer num;

    public Tag addNum() {
        num++;
        return this;
    }
}

