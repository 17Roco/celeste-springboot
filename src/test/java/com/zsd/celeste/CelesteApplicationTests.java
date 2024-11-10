package com.zsd.celeste;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zsd.celeste.entity.PO.Article;
import com.zsd.celeste.entity.PO.User;
import com.zsd.celeste.entity.form.ArticleFilterForm;
import com.zsd.celeste.mapper.ArticleMapper;
import com.zsd.celeste.mapper.UserMapper;
import com.zsd.celeste.service.FileResourceService;
import com.zsd.celeste.service.UserService;
import com.zsd.celeste.service.impl.FileResourceServiceImpl;
import com.zsd.celeste.util.HashUtil;
import com.zsd.celeste.util.TimeUtil;
import lombok.SneakyThrows;
import org.junit.jupiter.api.MethodDescriptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


@SpringBootTest
class CelesteApplicationTests {

    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    UserService userService;
    @Autowired
    HashUtil hashUtil;
    @Autowired
    FileResourceService fileResourceService;
    @Autowired
    UserMapper userMapper;



//    @Test
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

    @Test
    void a() {
        Page<User> page = Page.of(1, 10);
        userMapper.getFollowedList(page, 4).getRecords().forEach(System.out::println);
    }

}
