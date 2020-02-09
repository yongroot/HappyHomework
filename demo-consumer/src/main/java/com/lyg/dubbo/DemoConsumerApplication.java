package com.lyg.dubbo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * 服务消费者模块
 */
@SpringBootApplication
@ImportResource({"classpath:consumer.xml"})
public class DemoConsumerApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoConsumerApplication.class,args);
    }
}
