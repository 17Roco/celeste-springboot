package com.zsd.celeste;


import com.zsd.celeste.util.link.LinkMapper;
import com.zsd.celeste.service.ArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LinkTest {

    @Autowired
    ArticleService articleService;
    @Autowired
    LinkMapper linkMapper;
    @Test
    void linkTest(){
//        LinkConfig config = new LinkConfig("link_aid_tid", "aid", "tid");
//        System.out.println(linkMapper.getLinkFieldCount(config, 2));
//        System.out.println(linkMapper.getLinkField(config, 2));
//        System.out.println("----------------------------------");
//        System.out.println(linkMapper.addLink(config, 2, 100));
//        System.out.println(linkMapper.getLinkField(config, 2));
//        System.out.println("----------------------------------");
//        System.out.println(linkMapper.delLink(config, 2, 100));
//        System.out.println(linkMapper.getLinkField(config, 2));
    }

    @Test
    void artTag(){
//        articleService.select(w->w.eq("aid",1)).forEach(article -> {
//            article.setContext("");
//            System.out.println(article);
//        });
    }
}
