package com.zsd.celeste;

import com.zsd.celeste.service.ArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CelesteApplicationTests {

    @Autowired
    ArticleService articleService;
    @Test
    void contextLoads() {
        System.out.println(articleService.like(1, 11, true));
    }

}
