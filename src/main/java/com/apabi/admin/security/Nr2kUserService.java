package com.apabi.admin.security;

import com.apabi.admin.entity.AuthPermission;
import com.apabi.admin.entity.AuthUser;
import com.apabi.admin.mapper.AuthPermissionMapper;
import com.apabi.admin.mapper.AuthUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Nr2kUserService implements UserDetailsService { //自定义UserDetailsService 接口

    @Autowired
    AuthUserMapper userMapper;
    @Autowired
    AuthPermissionMapper permissionMapper;

    @Override
    public UserDetails loadUserByUsername(String username) {
        AuthUser user = userMapper.findByUserName(username);
        if (user != null) {
            List<AuthPermission> permissions = permissionMapper.findByUserId(user.getId());
            List<GrantedAuthority> grantedAuthorities = new ArrayList <>();
            for (AuthPermission permission : permissions) {
                if (permission != null && permission.getName()!=null) {

                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getName());
                    grantedAuthorities.add(grantedAuthority);
                }
            }
            return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
        } else {
            throw new UsernameNotFoundException("admin: " + username + " do not exist!");
        }
    }

}