package com.apabi.admin.service;

import com.apabi.admin.entity.AuthUser;

/**
 * Created by liuyutong on 2018/1/18.
 */
public interface AuthUserService {
    /**
     * 获取当前登录用户
     * @return AuthUser
     */
    AuthUser getCurrUser();
}
