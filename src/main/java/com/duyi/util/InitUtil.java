package com.duyi.util;

import com.duyi.admin.domain.AdminPower;
import com.duyi.admin.service.AdminService;
import com.duyi.datatransfer.aop.DataTransferAOP;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class InitUtil {
    @Autowired
    AdminService adminService;

    public static List<AdminPower> adminPowerList ;
    public static Set<String> adminPowerSet = new HashSet<>();

    @Pointcut("execution(* com.duyi..*Dao.*(..))")
    private void pointCut(){};

    @PostConstruct
    @Before("pointCut()")
    public void executeInit(JoinPoint joinPoint) {
        System.out.println("执行了AOP1");
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        DataTransferAOP dataTransferAOP = signature.getMethod().getDeclaredAnnotation(DataTransferAOP.class);
        System.out.println("执行了AOP2");
        if(dataTransferAOP != null) {
            System.out.println("执行AOP3");
            adminPowerList =  adminService.queryAll();
            for(AdminPower admin :adminPowerList) {
                adminPowerSet.add(getPowerkey(admin.getAccount(), admin.getUrl()));
            }
        }
    }

    public static String getPowerkey(String account, String url) {
        return account + "-" + url;
    }
    public static boolean hasPower(String account, String url) {
        return adminPowerSet.contains(getPowerkey(account,url));
    }
}
