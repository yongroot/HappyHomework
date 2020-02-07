package com.lyg.blog.service;

import com.lyg.blog.pojo.UserPlus;

/**
 * 账号其他信息
 * Created by winggonLee on 2020/2/7
 */
public interface UserPlusService {

    boolean insert(UserPlus userPlus);

    boolean deleteById(String id);

    boolean update(UserPlus userPlus);

    UserPlus getOne(UserPlus userPlus);
}
