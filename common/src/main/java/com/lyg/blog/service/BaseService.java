package com.lyg.blog.service;

import java.util.Collection;
import java.util.List;

/**
 * @Author 李永根
 * @Date 2020/2/4 14:30
 * @Email a460015041@gmail.com
 */
public interface BaseService<T> {

    boolean insert(T t);

    boolean insertAll(Collection<T> t);

    boolean deleteById(String id);

    boolean update(T t);

    T getOne(T t);
}
