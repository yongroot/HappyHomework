package com.lyg.blog.plus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by winggonLee on 2020/2/7
 */
//@Configuration
@WebFilter(urlPatterns = "/*", filterName = "第一过滤器")
public class MyWebFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(MyWebFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {
        logger.info(filterConfig.getFilterName() + "初始化");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        logger.info("ip: ", request.getRemoteAddr());
        logger.info(request.getMethod() + ':' + request.getRequestURL());
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            logger.info("cookies: " + Arrays.toString(cookies));
        }
        logger.debug("MyWebFilter.doFilter开始计时");
        long startTime = System.currentTimeMillis();
        filterChain.doFilter(servletRequest, servletResponse);
        logger.debug("MyWebFilter.doFilter计时结束，耗时:" + (System.currentTimeMillis() - startTime) + "ms");
    }

    @Override
    public void destroy() {
        System.out.println("过滤器销毁");
    }
}
