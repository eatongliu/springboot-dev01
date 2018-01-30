package com.apabi.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/admin")
public class LoginController {

    @RequestMapping("/login")
    public String login(){
        return "admin/login";
    }
    @RequestMapping("/index")
    public String index(){ return "admin/index";}
}
