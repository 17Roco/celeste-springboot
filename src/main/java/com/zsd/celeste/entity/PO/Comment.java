package com.zsd.celeste.entity.PO;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zsd.celeste.entity.PO.base.UserPojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("comment")
public class Comment implements UserPojo {
    @TableId("cid")
    private Integer cid;
    @TableField("text")
    private String text;
    @TableField("likee")
    private Integer likee;
    @TableField("time")
    private Date time;


    @JsonIgnore
    @TableField("pid")
    private Integer pid;
    @JsonIgnore
    @TableField("type")
    private Integer type;
    @JsonIgnore
    @TableField("uid")
    private Integer uid;
    @JsonIgnore
    @TableField("def_flag")
    @TableLogic()
    private Integer defFlag;
    @Override
    public Integer Id() {
        return getCid();
    }
    @Override
    public void Id(Integer id) {
        setCid(id);
    }



    /**
     * 评论所属的用户
     * */
    @TableField(exist = false)
    private User user;
    /**
     * 是否点赞
     * */
    @TableField(exist = false)
    private Boolean isLike;
    /**
     * 子评论数量
     * */
    @TableField(exist = false)
    private  Integer childrenCount;
    /**
     * 子评论
     * */
    @TableField(exist = false)
    private IPage<Comment> comments;

    /**
     * 点赞
     * */
    public Comment addLike(int i){
        likee += i;
        return this;
    }

}

