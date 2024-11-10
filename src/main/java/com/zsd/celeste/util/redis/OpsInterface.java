package com.zsd.celeste.util.redis;

import org.springframework.data.redis.core.HashOperations;

public interface OpsInterface<T> {
    void ops(T operations);
}
