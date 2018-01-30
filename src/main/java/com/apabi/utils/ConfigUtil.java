package com.apabi.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Configuration
@PropertySource("classpath:properties/config.properties")
public class ConfigUtil {
	@Autowired
	Environment env;

	public String get(String key) {
		return env.getProperty(key);
	}

	public static String getSystemProperty(String key) throws Exception{
		return System.getProperty(key);
	}

	public static String getRootPath() throws Exception{
		String dir = getSystemProperty("catalina.home");
		if(StringUtils.isNotBlank(dir)){
			return dir + "/webapps";
		}
		return null;
	}

}
