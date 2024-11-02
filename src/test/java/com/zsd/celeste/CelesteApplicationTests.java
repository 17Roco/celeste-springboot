package com.zsd.celeste;

import com.zsd.celeste.entity.ArticleFilter;
import com.zsd.celeste.entity.PO.Article;
import com.zsd.celeste.entity.form.FilterForm;
import com.zsd.celeste.mapper.ArticleMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@SpringBootTest
class CelesteApplicationTests {

    @Autowired
    ArticleMapper articleMapper;
    @Test
    void contextLoads() throws ParseException {

        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        Date date = ft.parse("2023-11-1");
        Date date1 = ft.parse("2024-6-1");

        FilterForm filterForm = new FilterForm(1, "create_time", null, date, date1, null);
        System.out.println(filterForm.getOrder());
        System.out.println(filterForm.getTag());

        List<Article> filter = articleMapper.filter(filterForm);
        System.out.println(filter.size());
        for (Article a:filter){
            System.out.println(a);
        }

    }

    @Test
    void a() throws ParseException {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        System.out.println(ft.parse("2022-1-1"));
    }

}
