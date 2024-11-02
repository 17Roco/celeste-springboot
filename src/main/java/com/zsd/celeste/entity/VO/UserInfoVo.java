package com.zsd.celeste.entity.VO;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

@Data
public class UserInfoVo {
    private Integer uid;
    private String username;
    private Integer sex;
    private Date birthday;
    private String sign;
}
