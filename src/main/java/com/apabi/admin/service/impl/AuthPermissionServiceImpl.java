package com.apabi.admin.service.impl;

import com.apabi.admin.entity.AuthPermission;
import com.apabi.admin.mapper.AuthPermissionMapper;
import com.apabi.admin.service.AuthPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthPermissionServiceImpl implements AuthPermissionService{
    @Autowired
    private AuthPermissionMapper permissionMapper;

    /**
     * 当前用户可以访问的菜单列表
     */
    @Override
//    @Cacheable("loadPermissions")
    public List<AuthPermission> findMenus(Integer userId, AuthPermission permission) {
        Integer pid = 0;
        if (permission != null) {
            pid = permission.getId();
        }
        List<AuthPermission> menus = permissionMapper.findMenusByUserIdOrPid(userId, pid);
        menus.forEach(one -> {
            List<AuthPermission> subMenus = this.findMenus(userId, one);
            if (subMenus == null || subMenus.isEmpty()) return;
            one.setSubPermission(subMenus);
        });
        return menus;
    }

    /**
     * 加载权限表中所有权限
     */
    @Override
    @Cacheable("loadPermissions")
    public Map<String, Collection<ConfigAttribute>> loadResourceDefine(){
        Map<String, Collection<ConfigAttribute>> map = new HashMap<>();
        Collection<ConfigAttribute> array;
        ConfigAttribute cfg;
        List<AuthPermission> permissions = permissionMapper.findAll();
        for(AuthPermission permission : permissions) {
            array = new ArrayList<>();
            cfg = new SecurityConfig(permission.getName());
            //此处只添加了用户的名字，其实还可以添加更多权限的信息，例如请求方法到ConfigAttribute的集合中去。此处添加的信息将会作为MyAccessDecisionManager类的decide的第三个参数。
            array.add(cfg);
            //用权限的getUrl() 作为map的key，用ConfigAttribute的集合作为 value，
            map.put(permission.getUrl(), array);
        }
        return map;
    }
}
