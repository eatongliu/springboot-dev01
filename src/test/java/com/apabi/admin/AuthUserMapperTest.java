package com.apabi.admin;

import com.apabi.admin.entity.AuthUser;
import com.apabi.admin.mapper.AuthUserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthUserMapperTest {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    AuthUserMapper authUserMapper;

    @Test
    public void testFindByUserName() {
        AuthUser admin = authUserMapper.findByUserName("admin");
        System.out.println(admin);
    }
}