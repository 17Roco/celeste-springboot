package com.zsd.celeste.entity.PO;


import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

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
public class User{
    @TableId("uid")
    private Integer uid;

    @TableField("sex")
    private Integer sex;
    @TableField("username")
    private String username;
    @TableField("img")
    private String img;
    @TableField("phone")
    private String phone;
    @TableField("email")
    private String email;

    @TableField("password")
    private String password;

    @JsonIgnore
    @TableField("status")
    private Integer status;
    @JsonIgnore
    @TableLogic
    @TableField("def_flag")
    private Integer defFlag;

}

