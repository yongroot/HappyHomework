package com.lyg.blog.mapper;

import java.util.Collection;
import java.util.List;

/**
 * @Author 李永根
 * @Date 2020/2/4 14:30
 * @Email a460015041@gmail.com
 */
public interface BaseMapper<T> {

    int insert(T t);

    int deleteById(String id);

    int update(T t);

    T getOne(String id);

    List<T> list();

    List<T> listByIds(Collection<String> ids);
}
