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
    @TableField("pid")
    private Integer pid;
    @JsonIgnore
    @TableField("type")
    private Integer type;
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

    public Comment(Integer cid, Integer pid, Integer type, Integer uid, String text) {
        this.cid = cid;
        this.pid = pid;
        this.type = type;
        this.uid = uid;
        this.text = text;
    }
    public Comment addLike(int i){
        like += i;
        return this;
    }
}

