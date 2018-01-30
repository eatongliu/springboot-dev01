package com.apabi.admin;

import com.apabi.admin.entity.AuthPermission;
import com.apabi.admin.mapper.AuthPermissionMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthPermissionMapperTest {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    AuthPermissionMapper authPermissionMapper;

    @Test
    public void testFindByUserId() {
        List<AuthPermission> permissions = authPermissionMapper.findByUserId(1);
        System.out.println(permissions);
    }
}
