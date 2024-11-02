package com.zsd.celeste.entity.form;

import lombok.Data;

import java.util.Date;

@Data
public class UserInfoForm {

    private Integer sex;
    private String username;
    private Date birthday;
    private String sign;



}
