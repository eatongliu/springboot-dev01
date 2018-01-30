package com.apabi.common.config;

import com.apabi.admin.security.Nr2kAuthTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Created by liuyutong on 2018/1/18.
 */
@Configuration
public class FreeMarkerConfig {

    @Autowired
    private freemarker.template.Configuration configuration;

    @Autowired
    private Nr2kAuthTag nr2kAuthTag;

    @PostConstruct
    public void setSharedVariable(){
        configuration.setSharedVariable("nr2kAuthTag", nr2kAuthTag);
    }
}
