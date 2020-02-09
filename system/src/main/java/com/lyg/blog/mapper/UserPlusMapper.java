package com.lyg.blog.mapper;

import com.github.pagehelper.Page;
import com.lyg.blog.mapper.provider.UserPlusMapperProvider;
import com.lyg.blog.pojo.UserPlus;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author 李永根
 * @Date 2020/2/5 12:50
 * @Email a460015041@gmail.com
 */
@Mapper
public interface UserPlusMapper {

    @InsertProvider(type = UserPlusMapperProvider.class, method = "insert")
    boolean insert(@Param("base") UserPlus userPlus);

    @UpdateProvider(type = UserPlusMapperProvider.class, method = "update")
    boolean update(@Param("base") UserPlus userPlus);

    @SelectProvider(type = UserPlusMapperProvider.class, method = "getOne")
    UserPlus getOne(@Param("base") UserPlus userPlus);

    @SelectProvider(type = UserPlusMapperProvider.class, method = "list")
    List<UserPlus> list(@Param("base") UserPlus userPlus, @Param("page") Page page);
}
