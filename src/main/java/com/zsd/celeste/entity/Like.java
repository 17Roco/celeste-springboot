package com.zsd.celeste.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("likee")
public class Like {
    @TableField("uid")
    private Integer uid;
    @TableField("obj_id")
    private Integer objId;
    @TableField("type")
    private Integer type;
}
