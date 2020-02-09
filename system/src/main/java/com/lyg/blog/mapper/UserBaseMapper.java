package com.lyg.blog.mapper;

import com.lyg.blog.mapper.provider.UserBaseMapperProvider;
import com.lyg.blog.pojo.UserBase;
import org.apache.ibatis.annotations.*;

import java.util.Collection;

/**
 * @Author 李永根
 * @Date 2020/2/4 14:42
 * @Email a460015041@gmail.com
 */
@Mapper
public interface UserBaseMapper {

    @Select("select count(1) from t_user where account = #{base.account} and passWord = #{base.passWord}")
    boolean loginIn(@Param("base") UserBase userBase);

    @Select("select salt from t_user where account = #{account}")
    Integer getSalt(@Param("account") String account);

    @InsertProvider(type = UserBaseMapperProvider.class, method = "insert")
    boolean insert(@Param("base") UserBase userBase);

    @InsertProvider(type = UserBaseMapperProvider.class, method = "insertAll")
    int insertAll(@Param("base") Collection<UserBase> userBase);

    @UpdateProvider(type = UserBaseMapperProvider.class, method = "update")
    boolean update(@Param("base") UserBase userBase);

    @SelectProvider(type = UserBaseMapperProvider.class, method = "getOne")
    UserBase getOne(@Param("base") UserBase userBase);
}
