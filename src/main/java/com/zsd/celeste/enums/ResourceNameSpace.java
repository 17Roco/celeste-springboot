package com.zsd.celeste.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResourceNameSpace {
    IMAGE_USER("user"),
    IMAGE_ARTICLE("article");

    private final String path;
}
