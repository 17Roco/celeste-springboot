package com.zsd.celeste;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsd.celeste.entity.PO.Article;
import com.zsd.celeste.entity.form.ArticleFilterForm;
import com.zsd.celeste.mapper.ArticleMapper;
import com.zsd.celeste.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@SpringBootTest
class CelesteApplicationTests {

    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    UserService userService;
    @Test
    void contextLoads() throws ParseException {

        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        Date date = ft.parse("2023-11-1");
        Date date1 = ft.parse("2024-6-1");

        ArticleFilterForm filterForm = new ArticleFilterForm(1, "create_time", null, null, null, null,null);
        System.out.println(filterForm.getOrder());
        System.out.println(filterForm.getTag());

        Page<Article> page = Page.of(1, 10);
        Page<Article> filter = articleMapper.filter(page, filterForm);
        System.out.println(filter);
        filter.getRecords().forEach(System.out::println);

    }

//    @Test
    void a() throws ParseException {
        userService.getById(1);
    }

}
