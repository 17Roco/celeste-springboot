package com.zsd.celeste.entity.PO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zsd.celeste.entity.DO.ArticleUpdate;
import com.zsd.celeste.entity.VO.UserInfoVo;
import com.zsd.celeste.util.AutUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.util.StringUtils;

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
    private Integer like;
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
    private UserInfoVo user;


    public void update(ArticleUpdate update){
        setTitle(update.getTitle());
        setContext(update.getContext());
        setUpdateTime(new Date());
    }
    public Article(Integer aid,Integer uid,ArticleUpdate update){
        this.aid = aid;
        this.uid = uid;
        update(update);
    }
    public Article changeLike(Integer i){
        like += i;
        return this;
    }
}

