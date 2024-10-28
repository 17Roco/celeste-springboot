package com.zsd.celeste.entity.PO;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("comment")
public class Comment {
    @TableId("cid")
    private Integer cid;
    @TableField("pcid")
    private Integer pcid;
    @TableField("aid")
    private Integer aid;
    @TableField("uid")
    private Integer uid;
    @TableField("text")
    private String text;
    @TableField("likee")
    private Integer like;
    @TableField("time")
    private Date time;

}

