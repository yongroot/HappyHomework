package com.lyg.blog.plus;

/**
 * @Author 李永根
 * @Date 2020/2/4 17:37
 * @Email a460015041@gmail.com
 */
//@Intercepts(@Signature(type = UserCollection.class,method = "getSomething",args = {ResponseDomain.class}))
public class LoginInterceptor /*implements Interceptor*/ {
   /* private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        logger.info(this.getClass().getName() + "intercept...");
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object o) {
        logger.info(this.getClass().getName() + "plugin...");
        System.out.println(o);
        return null;
    }

    @Override
    public void setProperties(Properties properties) {
        logger.info(this.getClass().getName() + "setProperties...");
        System.out.println(properties.propertyNames());
    }*/
}
