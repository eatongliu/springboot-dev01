package com.apabi.admin.service;

import com.apabi.admin.entity.AuthPermission;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.ConfigAttribute;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface AuthPermissionService {
    /**
     * 当前用户可以访问的菜单列表
     */
    List<AuthPermission> findMenus(Integer userId, AuthPermission permission);

    /**
     * 加载权限表中所有权限
     */
    Map<String, Collection<ConfigAttribute>> loadResourceDefine();
}
