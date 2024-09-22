package com.zsd.celeste;

import com.zsd.celeste.entity.PO.Article;
import com.zsd.celeste.entity.PO.Tag;
import com.zsd.celeste.service.ArticleService;
import com.zsd.celeste.service.TagService;
import com.zsd.celeste.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.Date;
import java.util.List;
import java.util.Random;

@SpringBootTest
public class userTest {
    @Autowired
    private UserService userService;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private TagService tagService;
    @Test
    void addTestUser100(){
        for (int i = 1; i <= 100; i++) {
            System.out.printf("('user%d')%s",i,(i%20==0) ? ",\n" : ",");
        }
    }
    @Test
    void addArticle100(){
        Random random = new Random();

        for (int i = 1; i <= 100; i++) {
            System.out.printf(
                    "(%d,'title %d','sdjghyuig;;eiruag%derui;uhegrh%duidgsdfhsiuef%dhushuia%ddgshudgfa')%s",
                    random.nextInt(102),
                    random.nextInt(1000),
                    random.nextInt(1000),random.nextInt(1000),random.nextInt(1000),random.nextInt(1000),
                    (i%5==0) ? ",\n" : ","
            );
        }
    }
    @Test
    void addLink(){
        Random random = new Random();
        for (int i = 1; i <= 100; i++) {
            System.out.printf(
                    "(%d,%d)%s",
                    random.nextInt(101),
                    random.nextInt(111),
                    (i%20==0) ? ",\n" : ","
            );
        }
    }














//    @Test
    void UpdateTestArticle(){
        LocalDateTime now = LocalDateTime.now();
        Random tr = new Random();
        long count = articleService.count();
        System.out.println(count);
        for (int i = 1; i < count; i++,now = now.minusDays(tr.nextInt(1,7))) {
            Random r = new Random();
            Article a = new Article();
            a.setWatch(r.nextInt(5000));
            a.setAid(i);
            a.setLikee(r.nextInt(2000));
            a.setCreateTime(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()));
            a.setUpdateTime(a.getCreateTime());
            articleService.updateById(a);
        }
    }

//    @Test
    void UpdateTestArticleTagsLink(){
        long artCount = articleService.count();
        List<String> tags = tagService.list().stream().map(Tag::getTitle).toList();
        Random linkNumRan = new Random();
        for (int i = 1; i <= artCount; i++) {
            for (int j = 1; j < linkNumRan.nextInt(8); j++) {
                try {
                    articleService.addArticleTag(i,tags.get(linkNumRan.nextInt(tags.size())));
                }catch (Exception e){
                    System.out.println("x");
                }
            }
        }
    }

//    @Test
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
