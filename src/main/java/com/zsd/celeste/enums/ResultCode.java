package com.zsd.celeste.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {
    OK(200, "OK"),
    ERROR(500, "Error");

    private final int code;
    private final String text;
}
