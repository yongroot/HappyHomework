package com.lyg.blog.mapper.provider;

import com.lyg.blog.annotations.Column;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 通用mapperProvider
 *
 * @Author 李永根
 * @Date 2020/2/5 10:13
 * @Email a460015041@gmail.com
 */
public abstract class BaseMapperProvider implements BaseMapperInterface {

    // insert into tableName(tableField1,tableField2) values(#{base.objField1},#{base.objField2})
    public String insert(Map<String, Object> param) {
        Object baseObj = param.get("base");
        Class<?> aClass = baseObj.getClass();

        String table = getTableName();
        LinkedList<Field> fields = new LinkedList<>();
        LinkedList<String> tableFieldNames = new LinkedList<>();
        LinkedList<String> objFieldNames = new LinkedList<>();
        getAllFields(fields, aClass);
        for (Field field : fields) {
            Column column;
            // 对象属性可映射当前表
            if ((column = field.getAnnotation(Column.class)) != null && Arrays.asList(column.table()).contains(table)) {
                String tableFieldName = column.name();
                String objFieldName = field.getName();
                if ("".equals(tableFieldName)) tableFieldName = objFieldName; // 对象属性名默认为表格字段名称
                tableFieldNames.add(tableFieldName);
                objFieldNames.add(objFieldName);
            }
        }
        String sql = "insert into " + table + '(' + String.join(",", tableFieldNames)
                +") values(#{base."+String.join("},#{base.",objFieldNames)+"})";
        return sql;
    }

    // update tableName set tableField1 = #{base.objField1}, tableField1 = #{base.objField2} where tableKeyField = #{base.objKeyField}
    public String update(Map<String, Object> param) {
        Object baseObj = param.get("base");
        Class<?> aClass = baseObj.getClass();

        String table = getTableName();
        StringBuilder sql = new StringBuilder("update ").append(table).append(" set ");
        StringBuilder sql2 = new StringBuilder(" where ");
        LinkedList<Field> fields = new LinkedList<>();
        getAllFields(fields, aClass);
        try {
            for (Field field : fields) {
                Column column;
                if ((column = field.getAnnotation(Column.class)) != null && Arrays.asList(column.table()).contains(table)) {
                    field.setAccessible(true);

                    // 忽略值为null的属性
                    if (field.get(baseObj) != null) {
                        String tableFieldName = column.name();
                        String objFieldName = field.getName();
                        if ("".equals(tableFieldName)) tableFieldName = objFieldName;

                        // 分辨当前属性是否为更新凭证字段
                        StringBuilder basis = column.updateKey() ? sql2 : sql;
                        if (basis.lastIndexOf("}") == basis.length() - 1) {
                            basis.append(',');
                        }
                        basis.append(tableFieldName).append('=').append("#{base.").append(objFieldName).append('}');
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return sql.append(sql2).toString();
    }

    public String getOne(Map<String, Object> param) {
        return null;
    }

    private void getAllFields(List<Field> fields, Class clazz) {
        Class superClazz = clazz.getSuperclass();
        if (superClazz != null) {
            getAllFields(fields, superClazz);
        }
        Collections.addAll(fields, clazz.getDeclaredFields());
    }
}
