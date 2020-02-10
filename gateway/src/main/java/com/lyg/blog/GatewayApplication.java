package com.lyg.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//@SpringBootApplication
@ServletComponentScan
@SpringBootConfiguration
@EnableTransactionManagement
@SpringBootApplication
@ImportResource({"classpath:dubbo/*.xml"/*, "classpath:elastic/*.xml"*/})
//@PropertySource("classpath:jdbc.properties")
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
        System.out.println("又是新的一天，加油啊！");
    }

}
