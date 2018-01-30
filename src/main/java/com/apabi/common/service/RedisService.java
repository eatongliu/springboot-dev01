package com.apabi.common.service;

import org.springframework.data.redis.core.ZSetOperations;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public interface RedisService {
    /**
     * 是否存在key
     * @param key
     * @return
     */
    boolean hasKey(String key);

    /**
     * 删除reids key
     * @param key
     * @throws Exception
     */
    void removeKey(String key);

    /**
     * 实现命令：ttl key，以秒为单位，返回给定 key的剩余生存时间(TTL, time to live)。
     */
    long ttl(String key);

    /**
     * 存储String类型的数据
     * @param key
     * @param value
     * @throws Exception
     */
    void set(String key, String value);

    void set(String key, String value, long period, TimeUnit timeUnit);
    /**
     * 读取String类型的数据
     * @param key
     * @return
     * @throws Exception
     */
    String get(String key);
    /**
     * 存储Map型数据
     * @param key
     * @param set
     * @throws Exception
     */
    void hset(String key, Map<?, ?> set);
    /**
     * 读取Map型数据
     * @param key
     * @return
     * @throws Exception
     */
    Map<String, Object> hget(String key);

    /**
     * 删除hash键值对
     * @param key
     * @param hashKey
     * @return
     * @throws Exception
     */
    long removeHashEntry(String key, Object... hashKey);

    /**
     * 检测是否存在键为hashKey的键值对
     * @param key     redis key
     * @param hashKey hashKey
     * @return
     * @throws Exception
     */
    boolean hasHashKey(String key, String hashKey);
    /**
     * 存储List类型的数据
     * @param key
     * @param set
     * @throws Exception
     */
    void lpush(String key, List<?> set);
    /**
     * 读取List类型的数据
     * @param key
     * @return
     * @throws Exception
     */
    List<Object> lpop(String key);
    /**
     * 存储Set类型的数据
     * @param key
     * @param set
     * @throws Exception
     */
    void sadd(String key, Set<?> set) ;
    /**
     * 读取Set类型的数据
     * @param key
     * @return
     * @throws Exception
     */
    Set<Object> spop(String key);
    /**
     * 存储有序Set类型的数据
     * @param key
     * @param set
     * @throws Exception
     */
    void zadd(String key, Set<ZSetOperations.TypedTuple<Object>> set);
    /**
     * 读取有序Set类型的数据
     * @param key
     * @param increase 增序排列，默认true
     * @return
     * @throws Exception
     */
    Set<Object> zget(String key, Boolean increase);

    /**
     * 获取hash value
     * @param key
     * @param hashKey
     * @return
     * @throws Exception
     */
    Object hgetHashVal(String key, String hashKey);

    /**
     * 获取hash value
     * @param key
     * @param hashKey
     * @return
     * @throws Exception
     */
    void hsetHashVal(String key, String hashKey, Object hashVal);
}
