package com.apabi.redis;

import com.apabi.admin.entity.AuthUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Test
    public void testRedisConfig() {
        ValueOperations<Object, Object> ops = redisTemplate.opsForValue();
        ops.set("aaaa", "lalallala", 10, TimeUnit.SECONDS);
        Object aaaa = ops.get("aaaa");
        logger.debug("aaaa >>> {}", aaaa);

        AuthUser user = new AuthUser();
        user.setUsername("lyt");
        user.setPassword("123456");
        ops.set("bbbb", user, 10, TimeUnit.SECONDS);
        AuthUser bbbb = (AuthUser)ops.get("bbbb");
        logger.debug("bbbb >>> {}", bbbb);

        Map<String, Object> map = new HashMap<>();
        map.put("a", "aaa");
        map.put("b", "bbb");
        map.put("c", "ccc");
        HashOperations<Object, Object, Object> ops1 = redisTemplate.opsForHash();
        ops1.putAll("map", map);

    }
}
