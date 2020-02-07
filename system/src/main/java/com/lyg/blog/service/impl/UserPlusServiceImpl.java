package com.lyg.blog.service.impl;

import com.lyg.blog.mapper.UserPlusMapper;
import com.lyg.blog.pojo.UserPlus;
import com.lyg.blog.service.UserPlusService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by winggonLee on 2020/2/7
 */
@Service
public class UserPlusServiceImpl implements UserPlusService {

    @Resource
    private UserPlusMapper userPlusMapper;

    @Override
    public boolean insert(UserPlus userPlus) {
        return userPlusMapper.insert(userPlus);
    }

    @Override
    public boolean deleteById(String id) {
        return false;
    }

    @Override
    public boolean update(UserPlus userPlus) {
        return userPlusMapper.update(userPlus);
    }

    @Override
    public UserPlus getOne(UserPlus userPlus) {
        return null;
    }
}
