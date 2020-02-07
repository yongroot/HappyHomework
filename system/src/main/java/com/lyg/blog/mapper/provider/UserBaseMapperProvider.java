package com.lyg.blog.mapper.provider;

import java.util.Map;

/**
 * Created by winggonLee on 2020/2/6
 */
public class UserBaseMapperProvider extends BaseMapperProvider {


    public String loginIn(Map<String, Object> param) {
        return null;
    }

    private static String insertSql = null;

    public String insertAll(Map<String, Object> param) {
        System.out.println(param);
        return "select 1 from dual";
    }

    @Override
    public String getTableName() {
        return "t_user";
    }
}
