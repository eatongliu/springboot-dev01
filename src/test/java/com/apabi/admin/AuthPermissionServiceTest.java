package com.apabi.admin;

import com.apabi.admin.entity.AuthPermission;
import com.apabi.admin.service.AuthPermissionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthPermissionServiceTest {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private AuthPermissionService permissionService;

    @Test
    public void testFindMenus() {
        List<AuthPermission> menus = permissionService.findMenus(1, null);
        logger.debug("菜单列表： {}", menus);
    }

    @Test
    public void testLoadResourceDefine() {
        Map<String, Collection<ConfigAttribute>> map = permissionService.loadResourceDefine();
        logger.debug("map： {}", map);
    }
}