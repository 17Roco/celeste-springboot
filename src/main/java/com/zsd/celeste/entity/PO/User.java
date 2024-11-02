package com.zsd.celeste.entity.PO;


import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zsd.celeste.entity.VO.UserInfoVo;
import com.zsd.celeste.entity.form.UserInfoForm;
import lombok.*;

import java.util.Date;

/**
 * (User)实体类
 *
 * @author zsd
 * @since 2024-06-10 15:15:59
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")
public class User implements UserPojo{
    @TableId("uid")
    private Integer uid;

    @TableField("sex")
    private Integer sex;
    @TableField("username")
    private String username;
    @TableField("birthday")
    private Date birthday;
    @TableField("sign")
    private String sign;

    @TableField("img")
    private String img;
    @TableField("phone")
    private String phone;
    @TableField("email")
    private String email;
    @JsonIgnore
    @TableField("password")
    private String password;

    @TableField("follow")
    private Integer follow;
    @TableField("follower")
    private Integer follower;
    @TableField("status")
    private Integer status;
    @JsonIgnore
    @TableLogic
    @TableField("def_flag")
    private Integer defFlag;
    @TableField(exist = false)
    private Boolean isFollow;





    public User(Integer uid, UserInfoVo userInfo){
        this.uid = uid;
        this.username = userInfo.getUsername();
        this.sex = userInfo.getSex();
        this.birthday = userInfo.getBirthday();
        this.sign = userInfo.getSign();

    }

    public User update(UserInfoForm form){
        this.username = form.getUsername();
        this.sex = form.getSex();
        this.birthday = form.getBirthday();
        this.sign = form.getSign();
        return this;
    }


    @Override
    public Integer Id() {
        return getUid();
    }

    @Override
    public void Id(Integer id) {
        setUid(id);
    }
}

