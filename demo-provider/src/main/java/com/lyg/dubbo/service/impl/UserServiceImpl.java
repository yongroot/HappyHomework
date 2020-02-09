package com.lyg.dubbo.service.impl;

import com.lyg.dubbo.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @Author 李永根
 * @Date 2020/2/6 18:20
 * @Email a460015041@gmail.com
 */
@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Override
    public String getUserNameById() {
        logger.info("UserService收到dubbo访问");
        return "ok";
    }
}
