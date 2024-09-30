package com.zsd.celeste.entity.DO;

import lombok.Data;

@Data
public class UpdatePasswordDO {
    private String oldPassword;
    private String newPassword;
}
