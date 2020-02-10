package com.lyg.blog.plus;

import com.lyg.blog.annotations.Log;
import com.lyg.blog.pojo.vo.ResponseDomain;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 控制层切面日志系统
 * Created by winggonLee on 2020/2/7
 */
@Aspect
@Component
@Order(100)
public class ControllerLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(ControllerLogAspect.class);

    private ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<>();

    /**
     * 签名方法
     */
    @Pointcut("execution(public * com.lyg.blog.controller..*.*(..))")
    public void webLog() {
        System.out.println("WebLogAspect.webLog");
    }

    /**
     * 预处理
     */
//    @Before(value = "webLog()&& @annotation(controllerWebLog)")
    @Before(value = "webLog()&& @annotation(log)")
    public void doBefore(JoinPoint joinPoint, Log log) {
        // 接收到请求
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        // 记录请求内容，threadInfo存储所有内容
        Map<String, Object> threadInfo = new HashMap<>();
        threadInfo.put("url", request.getRequestURL());
        threadInfo.put("uri", request.getRequestURI());
        threadInfo.put("httpMethod", request.getMethod());
        threadInfo.put("ip", request.getRemoteAddr());
        threadInfo.put("classMethod", joinPoint.getSignature().getDeclaringTypeName() + '.' + joinPoint.getSignature().getName());
        threadInfo.put("args", Arrays.toString(joinPoint.getArgs()));
        threadInfo.put("userAgent", request.getHeader("User-Agent"));
        threadInfo.put("methodName", log.value());
        threadLocal.set(threadInfo);
    }

    /**
     * AOP
     */
    @Around(value = "webLog()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        logger.info("WebLogAspect.doAround开始计时");
        long startTime = System.currentTimeMillis();
        Object ob = proceedingJoinPoint.proceed();
        long takeTime = System.currentTimeMillis() - startTime;
        logger.info("WebLogAspect.doAround计时结束，耗时:" + takeTime + "ms");
        Map<String, Object> threadInfo = threadLocal.get();
        if (threadInfo != null) {
            threadLocal.set(threadInfo);
        }
        // 注入方法耗时信息
        if (ob instanceof ResponseDomain) {
            ((ResponseDomain) ob).setTimestamp(takeTime);
        }
        return ob;
    }

    /**
     * 正常退出处理
     */
    @AfterReturning(value = "webLog()&& @annotation(log)", returning = "ret")
    public void doAfterReturning(Log log, Object ret) throws Throwable {
        threadLocal.get().put("result", ret);
        if (log.save()) {
            //插入数据库操作
            //insertResult(threadInfo);
        }
    }


    /**
     * 异常退出处理
     */
    @AfterThrowing(value = "webLog()", throwing = "throwable")
    public void doAfterThrowing(Throwable throwable) {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        // 异常信息
        logger.error("{}接口调用异常，异常信息{}", sra.getRequest().getRequestURI(), throwable);
        // todo 返回友好提示
    }

}