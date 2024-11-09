package com.zsd.celeste.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResourceNameSpace {
    IMAGE_USER("user"),
    IMAGE_ARTICLE("article"),

    IMAGE_DEV("dev");

    private final String path;
}
