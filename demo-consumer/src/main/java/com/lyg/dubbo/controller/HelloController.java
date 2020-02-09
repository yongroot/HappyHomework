package com.lyg.dubbo.controller;


import com.lyg.dubbo.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author zhaopeng
 * @description: 请求controller
 * @date 2019/4/12
 */
@RestController
public class HelloController {
    @Resource
//    @Qualifier("userService")
    private UserService userService;

    @GetMapping("/sayHi")
    public String sayHi() {
        return userService.getUserNameById();
    }
}