package com.zsd.celeste.entity.PO;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("comment")
public class Comment implements UserPojo{
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
    @JsonIgnore
    @TableField("def_flag")
    @TableLogic()
    private Integer defFlag;


    @TableField(exist = false)
    private User user;
    @TableField(exist = false)
    private IPage<Comment> comments;

    public Comment(Integer cid, Integer pcid, Integer aid, Integer uid, String text) {
        this.cid = cid;
        this.pcid = pcid;
        this.aid = aid;
        this.uid = uid;
        this.text = text;
    }
}

