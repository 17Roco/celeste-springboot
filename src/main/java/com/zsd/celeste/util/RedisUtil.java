package com.zsd.celeste.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisUtil {
    @Autowired
    private RedisTemplate<String,Object> template;

    public void set(String key, Object value) {
        template.opsForValue().set(key, value);
    }

    public Object get(String key) {
        return template.opsForValue().get(key);
    }

}
