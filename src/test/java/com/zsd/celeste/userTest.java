package com.zsd.celeste;

import com.zsd.celeste.pojo.Article;
import com.zsd.celeste.pojo.User;
import com.zsd.celeste.service.ArticleService;
import com.zsd.celeste.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

@SpringBootTest
public class userTest {
    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;

    @Test
    void addTestUser100(){
        User user = new User();
        user.setPassword("$2a$10$.VXU9ZtIPk40SqQnMFI2zud6ht7xN5Z2hlFBCQoL.sAZA6lu/2rCG");
        for (int i = 0; i < 99; i++) {
            user.setUsername("user"+i);
            user.setUid(null);
            System.out.println(i+" : "+ userService.save(user));
        }
    }
    @Test
    void addTestArticle100(){

        for (int i = 0; i < 100; i++) {
            Random r = new Random();
            Article a = new Article();
            a.setAid(null);
            a.setUid(r.nextInt(1,101));
            a.setContext("test article " + i);
            a.setLike_(r.nextInt());
            a.setStatus(1);
            a.setTitle("test title " + i);
            a.setWatch(r.nextInt());
            articleService.save(a);
            System.out.println(i);
        }
    }
    @Test
    void UpdateTestArticle100(){
        for (int i = 1; i < 111; i++) {
            Random r = new Random();
            Article a = new Article();
            a.setWatch(r.nextInt(5000));
            a.setAid(i);
            a.setLike_(r.nextInt(2000));
            articleService.updateById(a);

        }
    }

    @Test
    void encoder(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("123456"));
    }

    @Test
    void del(){
        System.out.println(articleService.removeById(107));
    }

    @Test
    void timeTest(){
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now.getDayOfWeek().getValue());
        for (int i = 1; i < 9; i++) {
            LocalDateTime now2 = now.plusYears(i);
            System.out.println(now2.getDayOfWeek().getValue() + "  " + now2.getDayOfWeek());
        }
    }
}
