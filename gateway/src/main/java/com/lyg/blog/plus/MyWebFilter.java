package com.lyg.blog.plus;

import com.lyg.blog.pojo.UserBase;
import com.lyg.blog.service.UserBaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.stream.Stream;

/**
 * Created by winggonLee on 2020/2/7
 */
//@Configuration
@WebFilter(urlPatterns = "/*", filterName = "第一过滤器")
public class MyWebFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(MyWebFilter.class);


    @Autowired
    private UserBaseService userBaseService;


    @Override
    public void init(FilterConfig filterConfig) {
        logger.info(filterConfig.getFilterName() + "初始化");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        logger.info("ip: ", request.getRemoteAddr());
        logger.info(request.getMethod() + ':' + request.getRequestURL());

        String token = request.getHeader("token");
        if (token == null) {
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    token = cookie.getValue();
                }
            }
        }
        String[] accountInfo;
        if (token != null && (accountInfo = userBaseService.getToken().get(token)) != null) {
            Stream.of(accountInfo).forEach(System.out::println);
        }

        String uri = request.getRequestURI();
        if (uri.contains("loginIn")) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {

            HttpServletResponse response = (HttpServletResponse) servletResponse;
//        Cookie cookie = new Cookie("user_account",);
//        response.addCookie();
            System.out.println(response);
        }

    }

    @Override
    public void destroy() {
        System.out.println("过滤器销毁");
    }
}
