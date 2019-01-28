package com.duyi.management.filter;

import com.duyi.common.BaseController;
import com.duyi.management.domain.User;
import com.duyi.management.service.UserService;
import com.duyi.statistics.service.StatisticsService;
import com.duyi.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ApiFilter extends BaseController implements Filter {
    //    private static ApplicationContext ac;
//    private static UserService userService;
//    private static StatisticsService statisticsService;
    private final static Integer VIP_UPPER = 10000;
    private final static Integer NOR_UPPER = 100;
    @Autowired
    UserService userService;
    @Autowired
    StatisticsService statisticsService;
//    static {
//        ac = new FileSystemXmlApplicationContext("classpath:applicationContext.xml");
//        userService = (UserService) ac.getBean("userService");
//        statisticsService = (StatisticsService) ac.getBean("statisticsService");
//    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {


        HttpServletRequest request = (HttpServletRequest) servletRequest;

        HttpServletResponse response = (HttpServletResponse) servletResponse;

        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        String appkey = servletRequest.getParameter("appkey");
//        System.out.println(appkey);

        if (appkey == null) {
            writeResult(response, "fail", "缺少appkey", null);
            return;
        }

        User user = userService.queryByAppkey(appkey);

        if (user == null) {
            writeResult(response, "fail", "appkey不正确", null);
            return;
        }
        if (user.getStatus() != 1) {
            writeResult(response, "fail", "帐户未激活，请前往主页面激活", null);
            return;
        }

        //1.判断用户是否为会员（type!=0）

        Integer count = statisticsService.getPvCount(appkey, TimeUtil.getPreDate(0, TimeUtil.MomentEnum.FIRST_SECOND), TimeUtil.getPreDate(0, TimeUtil.MomentEnum.LAST_SECOND));
        if (count == null) {
            count = 0;
        }

        if (user.getType() != 0) {
            //用户是会员
            if (count >= VIP_UPPER) {
                writeResult(response, "fail", "已达今日上限" + VIP_UPPER, null);
                return;
            }
        } else {
            //  不是会员每天（count > 100）终止。
            if (count >= NOR_UPPER) {
                writeResult(response, "fail", "已达今日上限" + NOR_UPPER, null);
                return;
            }
        }


        String path = request.getRequestURI();
//        System.out.println(path);
        statisticsService.addCount(appkey, path);
        count++;
        System.out.println("今日使用次数" + count);
        filterChain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void destroy() {

    }

}
