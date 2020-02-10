package com.lyg.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@ServletComponentScan
@SpringBootConfiguration
@EnableTransactionManagement
@SpringBootApplication
@ImportResource({"classpath:dubbo/*.xml"/*, "classpath:elastic/*.xml"*/})
@MapperScan(basePackages = {"com.lyg.blog.mapper"})
public class SystemApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SystemApplication.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(args);
//        SpringApplication.run(SystemApplication.class, args)
        System.out.println("又是新的一天，加油啊！");
    }

}
