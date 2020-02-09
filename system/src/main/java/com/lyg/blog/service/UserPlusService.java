package com.lyg.blog.service;

import com.github.pagehelper.Page;
import com.lyg.blog.pojo.UserPlus;

import java.util.List;

/**
 * 账号其他信息
 * Created by winggonLee on 2020/2/7
 */
public interface UserPlusService {

    boolean insert(UserPlus userPlus);

    boolean update(UserPlus userPlus);

    UserPlus getOne(UserPlus userPlus);

    List<UserPlus> list(UserPlus userPlus, Page page);
}
