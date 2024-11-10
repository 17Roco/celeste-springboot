package com.zsd.celeste.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum Role {
    USER(1,"User"),
    ADMIN(2,"Admin");

    private final Integer value;
    private final String name;
}
