package com.lyg.blog.annotations;

import java.lang.annotation.*;

/**
 * 日志标志
 * Created by winggonLee on 2020/2/7
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Log {
    /**
     * 标记名称
     *
     * @return
     */
    String value() default "";

    /**
     * 是否需要持久化
     *
     * @return
     */
    boolean save() default false;
}