package com.zsd.celeste.util.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RedisTokenHash {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private HashOperations<String, String, Object> getHash(){
        return redisTemplate.opsForHash();
    }
    public String getNamespace() {
        return "token";
    }


    public Object get(String key) {
        return getHash().get(getNamespace(), key);
    }
    public void put(String key, Object value) {
        getHash().put(getNamespace(), key, value);
    }

    public void delete(String key) {
        getHash().delete(getNamespace(), key);
    }
    public void reDelete(Object value) {
        Map<String, Object> map = getHash().entries(getNamespace());
        map.forEach((k, v) -> {
            if(v.equals(value))delete(k);
        });
    }

    public Map<String, Object> getMap() {
        return getHash().entries(getNamespace());
    }
}
