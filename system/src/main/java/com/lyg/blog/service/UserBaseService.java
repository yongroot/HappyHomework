package com.lyg.blog.service;

import com.lyg.blog.pojo.UserBase;
import org.springframework.scheduling.annotation.Async;

/**
 * 基础性用户服务
 * Created by winggonLee on 2020/2/6
 */
public interface UserBaseService extends BaseService<UserBase> {

    /**
     * 验证登录账号密码
     * 账号密码正确创建并返回token，否则返回null
     * 返回token
     */
    String loginIn(UserBase userBase, boolean isBare);

    /**
     * 账号注销登录状态
     */
    @Async
    void loginOut(String account, String token);
}