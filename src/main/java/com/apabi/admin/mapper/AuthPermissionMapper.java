package com.apabi.admin.mapper;

import com.apabi.admin.entity.AuthPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by liuyutong on 2017/12/25.
 */
public interface AuthPermissionMapper {
    List<AuthPermission> findAll();
    List<AuthPermission> findByConditions(Map<String, Object> params);
    List<AuthPermission> findByUserId(Integer userId);
    List<AuthPermission> findMenusByUserIdOrPid(@Param("userId") Integer userId,@Param("pid") Integer pid);
}
