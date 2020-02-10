package com.lyg.blog.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 联表辅助
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Connect {

    /**
     * 关联目标表名
     *
     * @return
     */
    String targetTable() default "";

    /**
     * 关联目标字段名
     *
     * @return
     */
    String targetName() default "";

    /**
     * 字段在数据库是否有索引
     * 联表时优化用
     *
     * @return
     */
    boolean haveIndex() default false;
}
