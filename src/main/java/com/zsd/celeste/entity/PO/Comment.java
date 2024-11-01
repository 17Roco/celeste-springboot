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
    @JsonIgnore
    @TableField("pid")
    private Integer pid;
    @JsonIgnore
    @TableField("type")
    private Integer type;
    @JsonIgnore
    @TableField("uid")
    private Integer uid;
    @TableField("text")
    private String text;
    @TableField("likee")
    private Integer likee;
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


    public Comment addLike(int i){
        likee += i;
        return this;
    }


    @Override
    public Integer getId() {
        return getCid();
    }

    @Override
    public void setId(Integer id) {
        setCid(id);
    }
}

