package com.duyi.management.filter;

import com.duyi.management.domain.User;
import com.duyi.management.service.UserLogService;
import com.duyi.management.service.UserService;
import com.duyi.util.RSAEncrypt;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter implements Filter {

    private static ApplicationContext ac;
    private static UserLogService userLogService;

    static {
        ac = new FileSystemXmlApplicationContext("classpath:applicationContext.xml");
//        userService = (UserService) ac.getBean("userService");
        userLogService = (UserLogService)ac.getBean("userLogService");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        HttpServletResponse response = (HttpServletResponse) servletResponse;

        StringBuffer path = ((HttpServletRequest) servletRequest).getRequestURL();

        Cookie[] cookies = ((HttpServletRequest) servletRequest).getCookies();

        for (Cookie cookie : cookies) {

            if ("uid".equals(cookie.getName())) {
                try {
                    String encodeSno = RSAEncrypt.decrypt(cookie.getValue());
                    User user = userLogService.findByAccount(encodeSno);
                    if (user != null) {
                        filterChain.doFilter(servletRequest, servletResponse);
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        response.sendRedirect("/login.html");
    }

    @Override
    public void destroy() {

    }

}
