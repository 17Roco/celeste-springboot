package com.zsd.celeste;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zsd.celeste.entity.PO.Comment;
import com.zsd.celeste.service.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@SpringBootTest
public class CommentTest {
    @Autowired
    private CommentService commentService;
//    @Test
    public void test() {

//        for (int i = 1; i <= 10; i++) {
//            Comment comment = new Comment(null,1,null,null,"comment"+i);
//            commentService.addComment(comment,1,1);
//        }
//        for (int i = 1; i <= 100; i++) {
//            Comment comment = new Comment(null,i/10,null,null,"comment--"+i);
//            commentService.addComment(comment,1,0);
//        }
    }

//    @Test
    public void test2() {
        IPage<Comment> comment = commentService.getArticleComment(1, 1);
        System.out.println(comment);
    }
}
