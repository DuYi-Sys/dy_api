package com.duyi.management.filter;

import com.duyi.common.BaseController;
import com.duyi.management.domain.User;
import com.duyi.management.service.UserService;
import com.duyi.statistics.service.StatisticsService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ApiFilter extends BaseController implements Filter {

    private static ApplicationContext ac;
    private static UserService userService;
    private static StatisticsService statisticsService;
    static {
        ac = new FileSystemXmlApplicationContext("classpath:applicationContext.xml");
        userService = (UserService) ac.getBean("userService");
        statisticsService = (StatisticsService) ac.getBean("statisticsService");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {


        HttpServletRequest request = (HttpServletRequest) servletRequest;

        HttpServletResponse response = (HttpServletResponse) servletResponse;

        response.setContentType("text/html;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        String appkey = servletRequest.getParameter("appkey");
        System.out.println(appkey);

        if (appkey == null) {
            writeResult(response, "fail", "缺少appkey", null);
            return;
        }
        
        User user = userService.queryByAppkey(appkey);

        if (user == null) {
            writeResult(response, "fail", "appkey不正确", null);
            return;
        }

        String path = request.getRequestURI();
        System.out.println(path);
        statisticsService.addCount(appkey,path);
        filterChain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void destroy() {

    }

}
