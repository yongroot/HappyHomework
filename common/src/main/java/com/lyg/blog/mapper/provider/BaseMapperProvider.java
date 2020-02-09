package com.lyg.blog.mapper.provider;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lyg.blog.annotations.Column;

import java.lang.reflect.Field;
import java.text.MessageFormat;
import java.util.*;

/**
 * 通用mapperProvider
 * Created by winggonLee on 2020/2/7
 */
public abstract class BaseMapperProvider implements BaseMapperInterface {


    //insert into tableName(tableField1,tableField2) values(#{base.objField1},#{base.objField2})
    public String insert(Map<String, Object> param) {

        String table = getTableName();
        StringBuilder sql = new StringBuilder("insert into ").append(table).append('(');
        StringBuilder sql2 = new StringBuilder(" values(");

        LinkedList<Field> fields = new LinkedList<>();
        getAllFields(fields, param.get("base").getClass());
        boolean flag = false;
        for (Field field : fields) {

            // 判断对象属性是否可映射当前表
            Column column = field.getAnnotation(Column.class);
            if (column != null && Arrays.asList(column.table()).contains(table)) {
                String tableFieldName = column.name();
                String objFieldName = field.getName();

                // 对象属性名默认为表格字段名称
                if ("".equals(tableFieldName)) {
                    tableFieldName = objFieldName;
                }

                if (flag) {
                    sql.append(',');
                    sql2.append(',');
                }
                sql.append(tableFieldName);
                sql2.append("#{base.").append(objFieldName).append('}');
                flag = true;
            }
        }
        sql.append(')').append(sql2.append(')'));
        return sql.toString();
    }

    //insert into table(tName1,tName2) values(#'{'base[{0}].objField1}},#'{'base[{0}].objField2}}),(#'{'base[{1}].objField1}},#'{'base[{1}].objField2}})
    public String insertAll(Map<String, Object> param) {
        Collection list = (Collection) param.get("base");
        StringBuilder sb = new StringBuilder();
        MessageFormat mf = null;
        int index = -1;
        for (Object obj : list) {
            if (index++ == -1) { // 狸猫换太子获得(#'{'base[{0}].objField1}})结构
                param.put("base", obj);
                String insertOneSql = insert(param);
                sb.append(insertOneSql.substring(0, insertOneSql.indexOf(")") + 1)).append(" values ");
                String valuesStr = insertOneSql.substring(insertOneSql.lastIndexOf('('))
                        .replace("{", "'{'")
                        .replace(".", "[{0}].");
                mf = new MessageFormat(valuesStr);
            }
            // 把base[{0}]里面的0替换成对象所在集合下标
            if (index > 0) {
                sb.append(',');
            }
            sb.append(mf.format(new Object[]{String.valueOf(index)}));
        }
        param.put("base", list);
        return sb.toString();
    }

    // update tableName set tableField1 = #{base.objField1}, tableField1 = #{base.objField2} where tableKeyField = #{base.objKeyField} and tableKeyField2 = #{base.objKeyField2}
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
                Column column = field.getAnnotation(Column.class);
                if (column != null && Arrays.asList(column.table()).contains(table)) {
                    field.setAccessible(true);

                    // 忽略值为null的属性
                    if (field.get(baseObj) != null) {
                        String tableFieldName = column.name();
                        String objFieldName = field.getName();
                        if ("".equals(tableFieldName)) tableFieldName = objFieldName;

                        // 分辨当前属性是否为更新凭证字段
                        boolean isKey = column.updateKey();
                        StringBuilder basis =  isKey? sql : sql2;
                        if (basis.lastIndexOf("}") == basis.length() - 1) {
                            basis.append(isKey ? " and " : " , ");
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

    // select field,field2 from table where key1=xx and key2=xx
    public String getOne(Map<String, Object> param) {
        return listAll(param) + " limit 1";
    }

    public String list(Map<String, Object> param) {
        String listSql = listAll(param);
        Page page = (Page)param.get("page");
        if (page != null) {
            int pageNum = page.getPageNum();
            int pageSize = page.getPageSize();
            listSql += " limit " + (pageNum - 1) * pageSize + ',' + pageSize;
        }
        return listSql;
    }

    private String listAll(Map<String, Object> param) {
        String table = getTableName();
        StringBuilder sql = new StringBuilder("select ");
        StringBuilder sql2 = new StringBuilder(" where ");

        LinkedList<Field> fields = new LinkedList<>();
        getAllFields(fields, param.get("base").getClass());

        boolean k = false;
        boolean f = false;
        for (Field field : fields) {
            Column column = field.getAnnotation(Column.class);
            if (column != null && Arrays.asList(column.table()).contains(table)) {
                String fieldName = !"".equals(column.name()) ? column.name() : field.getName();

                // 拼接where条件
                if (column.updateKey()) {
                    if (k) {
                        sql2.append(" and ");
                    }
                    sql2.append(fieldName).append('=').append("#{base.").append(field.getName()).append("} ");
                    k = true;
                }
                // 拼接select的字段
                if (f) {
                    sql.append(',');
                }
                sql.append(fieldName);
                f = true;
            }
        }
        if (!f) {
            sql.append('*');
        }
        if (!k) {
            sql2.append("1=0");
        }
        return sql.append(" from ").append(table).append(sql2).toString();
    }

    private void getAllFields(List<Field> fields, Class clazz) {
        Class superClazz = clazz.getSuperclass();
        if (superClazz != null) {
            getAllFields(fields, superClazz);
        }
        Collections.addAll(fields, clazz.getDeclaredFields());
    }
}
