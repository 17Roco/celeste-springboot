package com.zsd.celeste.entity.PO;


import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zsd.celeste.entity.VO.UserInfoVo;
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
    @TableField("password")
    private String password;

    @TableField("follow")
    private Integer follow;
    @TableField("follower")
    private Integer follower;
    @TableField("status")
    private Integer status;
    @TableLogic
    @TableField("def_flag")
    private Integer defFlag;



    public User(Integer uid, UserInfoVo userInfo){
        this.uid = uid;
        this.username = userInfo.getUsername();
        this.sex = userInfo.getSex();
        this.birthday = userInfo.getBirthday();
        this.sign = userInfo.getSign();

    }


    @Override
    public Integer getId() {
        return getUid();
    }

    @Override
    public void setId(Integer id) {
        setUid(id);
    }
}

