package com.duyi.util;

import com.duyi.admin.domain.Admin;
import com.duyi.admin.domain.AdminPower;
import com.duyi.admin.service.AdminService;
import com.duyi.admin.service.ResourcesService;
import com.duyi.admin.service.UserPowerService;
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

    @PostConstruct
    public void executeInit() {
        adminPowerList =  adminService.queryAll();
        System.out.println("-------------------");
        for(AdminPower admin :adminPowerList) {
//            System.out.println(admin);
            adminPowerSet.add(getPowerkey(admin.getAccount(), admin.getUrl()));
        }

        System.out.println("-------------------");
    }

    public static String getPowerkey(String account, String url) {
        return account + "-" + url;
    }
    public static boolean hasPower(String account, String url) {
        return adminPowerSet.contains(getPowerkey(account,url));
    }
}
