package com.zsd.celeste;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@SpringBootTest
class CelesteApplicationTests {

    @Test
    void contextLoads() {
//        System.out.println(new Date());
        System.out.println(LocalDateTime.now());
    }

}
