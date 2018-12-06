package com.duyi.management.filter;

import com.duyi.management.domain.User;
import com.duyi.management.service.UserService;
import com.duyi.util.RSAEncrypt;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserFilter implements Filter {

    private static ApplicationContext ac;

    private static UserService userService;

    static {
        ac = new FileSystemXmlApplicationContext("classpath:applicationContext.xml");

        userService = (UserService) ac.getBean("userService");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain fil) throws IOException, ServletException {
        System.out.println("filter execute");
        HttpServletResponse resp = (HttpServletResponse) res;
//
//        StringBuffer path = ((HttpServletRequest) req).getRequestURL();

        resp.setHeader("content-type", "application:json;charset=utf8");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "POST");
        resp.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");

        Cookie[] cookies = ((HttpServletRequest) req).getCookies();

        System.out.println(cookies);

//        System.out.println(cookies.length);
//        boolean b = false;
        int a = 0; //0:未登录


        for (Cookie cookie : cookies) {

            String name = cookie.getName();//sNo

//            System.out.println(cookie);

            System.out.println(name);

            String account = cookie.getValue();

//            System.out.println(account);

            if (account != null || !account.equals("")) {
                try {
                    String encodeSno = RSAEncrypt.decrypt(account);

                    System.out.println(encodeSno);

                    User user = userService.findByAccount(encodeSno);

                    //判断是否登录
                    if (user != null) {

                        if (user.getStatus() == 0) {

                            a = 1; //1:登录未激活

                        } else {

                            a = 2; //2:登录已激活

                            System.out.println("filter放行");

                            fil.doFilter(req, res);
                        }

                    }


                } catch (Exception e) {

                    e.printStackTrace();

                }
            }
        }

        if ( a == 0) {

            resp.sendRedirect("/login.html");

        } else if ( a == 1) { //登录未激活，跳转到激活页面

            req.getRequestDispatcher("/activate.html").forward(req, res);

        }
    }

    @Override
    public void destroy() {

    }
}
