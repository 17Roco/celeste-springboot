package com.zsd.celeste;

import com.zsd.celeste.service.ArticleService;
import com.zsd.celeste.util.link.LinkConfig;
import com.zsd.celeste.util.link.LinkMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
class CelesteApplicationTests {

    @Autowired
    ArticleService articleService;
    @Autowired
    LinkMapper linkMapper;
    @Test
    void contextLoads() {
//        System.out.println(articleService.like(1, 11, true));
//        LinkConfig linkConfig = new LinkConfig("link_article_like", "aid", "uid");
//        ArrayList<Integer> list = new ArrayList<>();
//        list.add(1);
//        list.add(2);
//        list.add(3);
//        list.add(4);
//        list.add(5);
//        System.out.println(linkMapper.inserts(linkConfig, 1, list));

    }

}
