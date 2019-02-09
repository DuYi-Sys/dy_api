package com.duyi.admin.filter;

import com.duyi.admin.domain.Admin;
import com.duyi.admin.domain.AdminPower;
import com.duyi.admin.service.AdminService;
import com.duyi.common.BaseController;
import com.duyi.common.RespStatusEnum;
import com.duyi.util.InitUtil;
import com.duyi.util.RSAEncrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AdminLoginFilter extends BaseController implements Filter {

    @Autowired
    AdminService adminService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        HttpServletResponse response = (HttpServletResponse) servletResponse;

        StringBuffer path = ((HttpServletRequest) servletRequest).getRequestURL();

        response.setContentType("text/html;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        Cookie[] cookies = ((HttpServletRequest) servletRequest).getCookies();
        if (cookies == null) {
            response.sendRedirect("/adminmanagement/adminLogin.html");
            return;
        }

        for (Cookie cookie : cookies) {
            if ("adminId".equals(cookie.getName())) {
                try {
                    String account = RSAEncrypt.decrypt(cookie.getValue());
                    Admin admin = adminService.queryByAccount(account);
                    String resource = request.getRequestURI();
                    if (admin != null && InitUtil.hasPower(admin.getAccount(), resource)) {
                        filterChain.doFilter(servletRequest, servletResponse);
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        response.sendRedirect("/adminmanagement/adminLogin.html");
    }


    @Override
    public void destroy() {

    }
}
