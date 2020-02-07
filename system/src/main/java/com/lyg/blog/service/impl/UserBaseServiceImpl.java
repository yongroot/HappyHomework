package com.lyg.blog.service.impl;

import com.lyg.blog.mapper.UserBaseMapper;
import com.lyg.blog.pojo.UserBase;
import com.lyg.blog.service.UserBaseService;
import com.lyg.blog.utils.MD5Util;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author 李永根
 * @Date 2020/2/4 14:31
 * @Email a460015041@gmail.com
 */
@Service
public class UserBaseServiceImpl implements UserBaseService {
    /**
     * 缓存登录信息, 减少db压力
     */
    private Map<String, String[]> TokenCache = new ConcurrentHashMap<>();

    @Resource
    private UserBaseMapper userBaseMapper;

    @Override
    public String loginIn(UserBase userBase, boolean isBare) {
        String account = userBase.getAccount();
        String passWord = userBase.getPassWord();
        String token = null;

        String[] userInfo = TokenCache.get(account);

        // 账号存在登录缓存，从缓存获取密码验证，正确直接弹回token
        if (userInfo != null && userInfo[1].equals(passWord)) {
            token = userInfo[2];
        }
        // 进行db验证账号密码，生成并缓存token
        else {
            int salt = userBaseMapper.getSalt(userBase.getAccount());
            userBase.setPassWord(MD5Util.getMD5(userBase.getPassWord() + salt));
            if (userBaseMapper.loginIn(userBase)) {
                TokenCache.put(account, new String[]{account, passWord, token = UUID.randomUUID().toString()});
            }
        }
        // 密码错误时token为null
        return token;
    }

    @Override
    public void loginOut(String account, String token) {
        String[] userCache = TokenCache.get(account);
        if (userCache != null && userCache[3].equals(token)) {
            TokenCache.remove(account);
        }
    }

    @Override
    public boolean insert(UserBase userBase) {
        userBase.setPassWord(addSalt(userBase).getPassWord());
        return userBaseMapper.insert(userBase);
    }

    @Override
    public boolean insertAll(Collection<UserBase> users) {
        if (users == null || users.isEmpty()) {
            return true;
        }
        users.forEach(this::addSalt);
        return userBaseMapper.insertAll(users) == users.size();
    }

    @Override
    public boolean deleteById(String id) {
        return false;
    }

    @Override
    public boolean update(UserBase userBase) {
        return false;
    }

    @Override
    public UserBase getOne(UserBase userBase) {
        return null;
    }

    /**
     * 附加两位数字进行md5密码加盐
     */
    private UserBase addSalt(UserBase userBase) {
        userBase.setSalt(new Random().nextInt(100));
        userBase.setPassWord(MD5Util.getMD5(userBase.getPassWord() + userBase.getSalt()));
        return userBase;
    }
}