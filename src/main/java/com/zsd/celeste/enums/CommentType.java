package com.zsd.celeste.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum CommentType {
    CHILDREN("children",0),
    ARTICLE("article",1);


    private final String name;
    private final int value;

}
