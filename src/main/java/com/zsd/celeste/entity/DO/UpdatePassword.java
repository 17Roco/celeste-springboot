package com.zsd.celeste.entity.DO;

import lombok.Data;

@Data
public class UpdatePassword {
    private String oldPassword;
    private String newPassword;
}
