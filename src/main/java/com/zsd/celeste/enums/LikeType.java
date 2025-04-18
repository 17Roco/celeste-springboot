package com.zsd.celeste.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LikeType {
    ARTICLE(1,"article"),
    COMMENT(2,"comment");

    private final Integer type;
    private final String name;

    public static LikeType getType(String  type) {
        for (LikeType likeType : LikeType.values()) {
            if (likeType.getName().equals(type)) {
                return likeType;
            }
        }
        return null;
    }
}
