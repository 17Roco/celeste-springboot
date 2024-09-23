package com.zsd.celeste.entity.VO;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class UserInfoVo {
    private Integer uid;
    private String username;
    private String email;
    private Integer sex;
    private String img;
    private String phone;
}
