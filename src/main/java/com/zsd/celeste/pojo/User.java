package com.zsd.celeste.pojo;


import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

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
    @TableField("password")
    private String password;
    @TableField("img")
    private String img;
    @TableField("phone")
    private String phone;
    @TableField("email")
    private String email;
    @TableField("status")
    private Integer status;
    @TableLogic
    @TableField("def_flag")
    private Integer defFlag;

}

