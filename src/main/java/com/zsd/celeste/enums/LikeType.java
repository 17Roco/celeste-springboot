package com.zsd.celeste.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LikeType {
    ARTICLE(1),
    COMMENT(2);

    private final Integer type;
}
