package com.apabi.admin.service.impl;

import com.apabi.admin.entity.AuthUser;
import com.apabi.admin.mapper.AuthUserMapper;
import com.apabi.admin.service.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * Created by liuyutong on 2018/1/18.
 */
@Service
public class AuthUserServiceImpl implements AuthUserService {
    @Autowired
    private AuthUserMapper userMapper;

    @Override
    public AuthUser getCurrUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userMapper.findByUserName(userDetails.getUsername());
    }
}
