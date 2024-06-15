package com.zsd.celeste;

import com.zsd.celeste.pojo.Article;
import com.zsd.celeste.pojo.User;
import com.zsd.celeste.service.ArticleService;
import com.zsd.celeste.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

@SpringBootTest
public class userTest {
    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;

//    @Test
//    void addTestUser100(){
//        User user = new User();
//        user.setPassword("$2a$10$.VXU9ZtIPk40SqQnMFI2zud6ht7xN5Z2hlFBCQoL.sAZA6lu/2rCG");
//        for (int i = 0; i < 99; i++) {
//            user.setUsername("user"+i);
//            user.setUid(null);
//            System.out.println(i+" : "+ userService.save(user));
//        }
//    }
//    @Test
//    void addTestArticle100(){
//
//        for (int i = 0; i < 100; i++) {
//            Random r = new Random();
//            Article a = new Article();
//            a.setAid(null);
//            a.setUid(r.nextInt(1,101));
//            a.setContext("test article " + i);
//            a.setLikee(r.nextInt());
//            a.setStatus(1);
//            a.setTitle("test title " + i);
//            a.setWatch(r.nextInt());
//            articleService.save(a);
//            System.out.println(i);
//        }
//    }
//    @Test
//    void UpdateTestArticle100(){
//        LocalDateTime now = LocalDateTime.now();
//        for (int i = 1; i < 111; i++,now = now.minusDays(1)) {
//            Random r = new Random();
//            Article a = new Article();
//            a.setWatch(r.nextInt(5000));
//            a.setAid(i);
//            a.setLikee(r.nextInt(2000));
//            a.setUpdateTime(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()));
//            articleService.updateById(a);
//        }
//    }

    @Test
    void encoder(){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("123456"));
    }

//    @Test
//    void del(){
//        System.out.println(articleService.removeById(107));
//    }

    @Test
    void timeTest(){
        LocalDate now = LocalDate.now();
        LocalDate monday = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        // 周日
        LocalDate sunday = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        System.out.println(monday);
        System.out.println(sunday);
        // 本周开始时间
        LocalDateTime weekStart = monday.atStartOfDay();
        // 本周结束时间
        LocalDateTime weekEnd = LocalDateTime.of(sunday, LocalTime.MAX);
        System.out.println(weekStart);
        System.out.println(weekEnd);
    }
}
