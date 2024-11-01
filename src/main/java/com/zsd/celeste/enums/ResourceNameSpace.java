package com.zsd.celeste.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResourceNameSpace {
    IMAGE_USER("userImage"),
    IMAGE_ARTICLE("articleImage");

    private final String path;
}
