package com.zsd.celeste;

import com.zsd.celeste.util.redis.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTest {

    @Autowired
    RedisTemplate<String, String> redisTemplate;
    @Test
    public void testRedis() {

        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();


    }
}
