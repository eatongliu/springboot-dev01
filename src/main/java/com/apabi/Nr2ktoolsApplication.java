package com.apabi;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ComponentScan(basePackages ="com.apabi.**")
@MapperScan("com.apabi.*.mapper")
@SpringBootApplication
@ServletComponentScan
@EnableTransactionManagement
public class Nr2ktoolsApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(Nr2ktoolsApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Nr2ktoolsApplication.class);
	}
}
