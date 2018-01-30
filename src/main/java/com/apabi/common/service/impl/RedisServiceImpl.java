package com.apabi.common.service.impl;

import com.apabi.common.service.RedisService;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class RedisServiceImpl implements RedisService {
    @Resource(name="redisTemplate")
    private RedisTemplate<Object, Object> redisTemplate;

    @Override
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public void removeKey(String key) {
        if(hasKey(key)){
            redisTemplate.delete(key);
        }
    }

    @Override
    public long ttl(String key) {
        return redisTemplate.getExpire(key);
    }

    @Override
    public void set(String key, String value) {
        ValueOperations<Object, Object> valueOps = redisTemplate.opsForValue();
        valueOps.set(key, value);
    }

    @Override
    public void set(String key, String value, long period, TimeUnit timeUnit) {
        ValueOperations<Object, Object> valueOps = redisTemplate.opsForValue();
        valueOps.set(key, value, period, timeUnit);
    }

    @Override
    public String get(String key) {
        ValueOperations<Object, Object> valueOps = redisTemplate.opsForValue();
        return (String)valueOps.get(key);
    }

    @Override
    public void hset(String key, Map<?, ?> set) {
        HashOperations<Object, Object, Object> hash = redisTemplate.opsForHash();
        hash.putAll(key, set);
    }

    @Override
    public Map<String, Object> hget(String key) {
        HashOperations<Object, String, Object> hash = redisTemplate.opsForHash();
        return hash.entries(key);
    }

    @Override
    public Object hgetHashVal(String key, String hashKey) {
        HashOperations<Object, String, Object> hash = redisTemplate.opsForHash();
        return hash.get(key, hashKey);
    }

    @Override
    public void hsetHashVal(String key, String hashKey, Object hashVal) {
        HashOperations<Object, String, Object> hash = redisTemplate.opsForHash();
        hash.put(key, hashKey, hashVal);
    }

    @Override
    public long removeHashEntry(String key, Object... hashKey) {
        HashOperations<Object, String, Object> hash = redisTemplate.opsForHash();
        return hash.delete(key, hashKey);
    }

    @Override
    public boolean hasHashKey(String key, String hashKey) {
        HashOperations<Object, String, Object> hash = redisTemplate.opsForHash();
        return hash.hasKey(key, hashKey);
    }

    @Override
    public void lpush(String key, List<?> set) {
        BoundListOperations<Object, Object> list = redisTemplate.boundListOps(key);
        list.rightPushAll(set);
    }

    @Override
    public List<Object> lpop(String key) {
        BoundListOperations<Object, Object> list = redisTemplate.boundListOps(key);
        return list.range(0, -1);
    }

    @Override
    public void sadd(String key, Set<?> set) {
        BoundSetOperations<Object, Object> bso = redisTemplate.boundSetOps(key);
        bso.add(set.toArray());
    }

    @Override
    public Set<Object> spop(String key) {
        BoundSetOperations<Object, Object> bso = redisTemplate.boundSetOps(key);
        return bso.members();
    }

    @Override
    public void zadd(String key, Set<ZSetOperations.TypedTuple<Object>> set) {
        BoundZSetOperations<Object, Object> bzop = redisTemplate.boundZSetOps(key);
        bzop.add(set);
    }

    @Override
    public Set<Object> zget(String key, Boolean increase) {
        BoundZSetOperations<Object, Object> bzop = redisTemplate.boundZSetOps(key);
        if(increase == null || increase){
            return bzop.range(0, -1);
        } else {
            return bzop.reverseRange(0, -1);
        }
    }
}