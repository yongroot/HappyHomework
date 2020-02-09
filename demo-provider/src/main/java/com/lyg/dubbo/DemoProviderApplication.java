package com.lyg.dubbo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@SpringBootApplication
@ImportResource({"classpath:provider.xml"})
public class DemoProviderApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DemoProviderApplication.class, args);
        System.out.println("服务端启动成功！！！");
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
