package com.apabi.admin.mapper;

import com.apabi.admin.entity.AuthUser;

/**
 * Created by liuyutong on 2017/12/25.
 */
public interface AuthUserMapper {
//    @Cacheable("user")
    AuthUser findByUserName (String username);
}
