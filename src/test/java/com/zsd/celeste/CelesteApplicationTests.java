package com.zsd.celeste;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;



@SpringBootTest
class CelesteApplicationTests {

    @Autowired
    RedisTemplate<Object,Object> redisTemplate;
    @Test
    void contextLoads() {
//        System.out.println(1);
        redisTemplate.opsForValue().set("celeste", "hello");
    }

    @Test
    void a(){
    }

}
