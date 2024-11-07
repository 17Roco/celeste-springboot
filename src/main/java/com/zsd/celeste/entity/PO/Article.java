package com.zsd.celeste.entity.PO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zsd.celeste.entity.form.ArticleForm;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * (Article)实体类
 *
 * @author zsd
 * @since 2024-06-10 15:15:58
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("article")
public class Article implements UserPojo{
    @TableId("aid")
    private Integer aid;

    @TableField("uid")
    private Integer uid;
    @TableField("title")
    private String title;
    @TableField("context")
    private String context;
    @TableField("img")
    private String img;
    @TableField("create_time")
    private Date createTime;
    @TableField("update_time")
    private Date updateTime;
    @TableField("watch")
    private Integer watch;
    @TableField("likee")
    private Integer likee;
    /**
     * 1:public,2:private,vip:3
     */
    @TableField("status")
    private Integer status;


    @JsonIgnore
    @TableField("def_flag")
    @TableLogic()
    private Integer defFlag;


    @TableField(exist = false)
    private List<String> tags = new ArrayList<>();
    @TableField(exist = false)
    private User user;
    @TableField(exist = false)
    private Boolean isLike;


    public Article update(ArticleForm update){
        setTitle(update.getTitle());
        setContext(update.getContext());
        setUpdateTime(new Date());
        return this;
    }
    public Article(Integer aid, Integer uid, ArticleForm update){
        this.aid = aid;
        this.uid = uid;
        update(update);
    }
    public Article changeLike(Integer i){
        likee += i;
        return this;
    }

    @Override
    public Integer Id() {
        return getAid();
    }
    @Override
    public void Id(Integer id) {
        setAid(id);
    }
}

