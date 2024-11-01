package com.zsd.celeste.entity.form;

import com.zsd.celeste.entity.PO.Comment;
import com.zsd.celeste.enums.CommentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentForm {
    private Integer pid;
    private String text;

    public Comment toComment(CommentType type) {
        Comment comment = new Comment();
        comment.setPid(pid);
        comment.setText(text);
        comment.setType(type.getValue());
        return comment;
    }
}
