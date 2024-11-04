package com.zsd.celeste.entity.form;

import lombok.Data;

@Data
public class ChangePasswordForm {
    private String oldPassword;
    private String newPassword;
}
