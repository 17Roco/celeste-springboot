package com.zsd.celeste.entity.PO;


import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zsd.celeste.entity.PO.base.UserPojo;
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
public class User implements UserPojo {
    @TableId("uid")
    private Integer uid;

    @TableField("username")
    private String username;
    @TableField("sex")
    private Integer sex;
    @TableField("birthday")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthday;
    @TableField("sign")
    private String sign;

    @TableField("img")
    private String img;
    @TableField("phone")
    private String phone;
    @TableField("email")
    private String email;

    @TableField("follow")
    private Integer follow;
    @TableField("followed")
    private Integer followed;
    @TableField("status")
    private Integer status;


    @JsonIgnore
    @TableField("password")
    private String password;
    @JsonIgnore
    @TableField("role")
    private Integer role;
    @JsonIgnore
    @TableLogic
    @TableField("def_flag")
    private Integer defFlag;
    @Override
    public Integer Id() {
        return getUid();
    }

    @Override
    public void Id(Integer id) {
        setUid(id);
    }



    /**
     * 是否关注
     * */
    @TableField(exist = false)
    private Boolean isFollow;
    /**
     * @param form 是否关注
     * 更新用户信息
     * */
    public User update(UserInfoForm form){
        this.username = form.getUsername();
        this.sex = form.getSex();
        this.birthday = form.getBirthday();
        this.sign = form.getSign();
        return this;
    }


}

