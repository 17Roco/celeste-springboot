package com.zsd.celeste.enums;

import com.zsd.celeste.entity.PO.base.Pojo;
import com.zsd.celeste.service.data.CommentService;
import com.zsd.celeste.util.base.BaseService;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum CommentType {
    CHILDREN("children",0),
    ARTICLE("article",1);


    private final String name;
    private final int value;

    static public CommentType getByName(String name) {
        for (CommentType commentType : CommentType.values()) {
            if (commentType.getName().equals(name)) {
                return commentType;
            }
        }
        return null;
    }

}
