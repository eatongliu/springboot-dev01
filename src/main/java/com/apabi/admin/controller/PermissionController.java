package com.apabi.admin.controller;

import com.apabi.admin.entity.AuthUser;
import com.apabi.admin.service.AuthPermissionService;
import com.apabi.admin.service.AuthUserService;
import com.apabi.common.vo.BeanResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by liuyutong on 2018/1/18.
 */
@Controller
public class PermissionController {
    private Logger logger = LoggerFactory.getLogger(PermissionController.class);
    @Autowired
    private AuthPermissionService permissionService;
    @Autowired
    private AuthUserService userService;

    @RequestMapping("/sidebar")
    @ResponseBody
    public BeanResult sidebarMenus() {
        try {
            AuthUser currUser = userService.getCurrUser();
            return BeanResult.success(permissionService.findMenus(currUser.getId(), null));
        }catch (Exception e) {
            logger.error("Exception: {}", e);
            return BeanResult.error(e.getMessage());
        }
    }
}
