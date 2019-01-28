package com.duyi.admin.controller;

import com.duyi.admin.domain.Admin;
import com.duyi.admin.domain.Resources;
import com.duyi.admin.domain.UserPower;
import com.duyi.admin.service.AdminService;
import com.duyi.admin.service.ResourcesService;
import com.duyi.admin.service.UserPowerService;
import com.duyi.common.BaseController;
import com.duyi.common.RespStatusEnum;
import com.duyi.util.MD5Util;
import com.duyi.util.RSAEncrypt;
import com.duyi.util.RegExUtil;
import com.duyi.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class AdminController extends BaseController {

    @Autowired
    AdminService adminService;
    @Autowired
    UserPowerService userPowerService;
    @Autowired
    ResourcesService resourcesService;
    @RequestMapping(value = "/admin/addAdmin", method = RequestMethod.GET)
    public void addAdmin(@RequestParam("account") String account,
                         @RequestParam("name") String name,
                         @RequestParam("password") String password,
                         @RequestParam("rePassword") String rePassword,
                         @RequestParam("email") String email,
                         HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        if (!RegExUtil.match("^[a-zA-Z0-9_]{4,16}$", account)) {
            writeResult(response, RespStatusEnum.FAIL.getValue(), "用户名必须为4-16位的字母数字下划线组成", null);
            return;
        }

        if (!RegExUtil.match("^[a-zA-Z0-9_]{6,16}$", password)) {
            writeResult(response, RespStatusEnum.FAIL.getValue(), "密码必须为6-16位的字母数字下划线组成", null);
            return;
        }

        if (!rePassword.equals(password)) {
            writeResult(response, RespStatusEnum.FAIL.getValue(), "两次密码输入不一致", null);
            return;
        }

        if (!RegExUtil.match("^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,5}$", email)) {
            writeResult(response, RespStatusEnum.FAIL.getValue(), "邮箱格式不正确", null);
            return;
        }

        boolean b = adminService.checkEmail(email.trim());
        if (b) {
            writeResult(response, RespStatusEnum.FAIL.getValue(), "此邮箱已经被注册过", null);
            return;
        }

        Admin admin = new Admin();
        admin.setAccount(account);
        admin.setName(name);
        admin.setPassword(MD5Util.MD5Encode(password, "utf8"));
        admin.setEmail(email.trim());
        admin.setCtime(TimeUtil.getNow());
        admin.setUtime(TimeUtil.getNow());
        AdminService.addAdminStatusEnum result = adminService.addAdmin(admin);
        writeResult(response, result.getStatusEnum().getValue(), result.getMsg(), null);

        if (result.getStatusEnum() == RespStatusEnum.SUCCESS) {
            //如果添加成功为管理员发送邮件
            String to = email;// 收件人
            String subject = "渡一教育平台帐户通知";
            adminService.sendEmail(account, password, to, subject);

        }

    }

    @RequestMapping(value = "/manage/adminLogin", method = RequestMethod.POST)
    public void adminLogin(@RequestParam("account") String account,
                           @RequestParam("password") String password,
                           HttpServletResponse response) throws Exception {

        response.setContentType("text/html;charset=utf-8");

        if (!RegExUtil.match("^[a-zA-Z0-9_]{4,16}$", account)) {
            writeResult(response, RespStatusEnum.FAIL.getValue(), "用户名必须为4-16位的字母数字下划线组成", null);
            return;
        }

        if (!RegExUtil.match("^[a-zA-Z0-9_]{6,16}$", password)) {
            writeResult(response, RespStatusEnum.FAIL.getValue(), "密码必须为6-16位的字母数字下划线组成", null);
            return;
        }

        AdminService.LoginStatusEnum result = adminService.login(account, password);
        if (result.getStatusEnum() == RespStatusEnum.SUCCESS) {
            String addminId = RSAEncrypt.encrypt(account);
            Cookie cookie = new Cookie("adminId", addminId);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
        writeResult(response, result.getStatusEnum().getValue(), result.getMsg(), "");

    }


    @RequestMapping(value = "/admin/addUserPower",method = RequestMethod.GET)
    public void addUserPower(@RequestParam("account") String account,
                             @RequestParam("powerId") Integer powerId,
                             HttpServletResponse response) throws IOException {

        response.setContentType("text/html;charset=utf-8");
        if (!RegExUtil.match("^[a-zA-Z0-9_]{4,16}$", account)) {
            writeResult(response, RespStatusEnum.FAIL.getValue(), "用户名必须为4-16位的字母数字下划线组成", null);
            return;
        }

        if (!RegExUtil.match("^[0-9]{1,4}$", powerId.toString())) {
            writeResult(response, RespStatusEnum.FAIL.getValue(), "权限必须是1-4位数字", null);
            return;
        }

        Admin admin = adminService.queryByAccount(account);
        if (admin == null) {
            writeResult(response, RespStatusEnum.FAIL.getValue(), "此管理员账号不存在", null);
            return;
        }
        UserPower userPower = new UserPower();
        userPower.setAccount(account);
        userPower.setPowerId(powerId);
        userPower.setCtime(TimeUtil.getDateTime());
        userPower.setUtime(TimeUtil.getDateTime());
        UserPowerService.UserPowerStatues result = userPowerService.addUserPower(userPower);
        writeResult(response, result.getStatusEnum().getValue(), result.getMsg(), null);
        //先写固定一个
        String power = "";
        if(powerId == 1) {
            power = "主管";
        } else if (powerId == 2) {
            power = "管理员";
        }
        if (result.getStatusEnum() == RespStatusEnum.SUCCESS) {
            String to = admin.getEmail();// 收件人
            String subject = "渡一教育平台权限通知";
            userPowerService.sendEmail(account, power, to, subject);
        }
    }

    @RequestMapping(value = "/admin/addResource",method = RequestMethod.GET)
    public void addResource(@RequestParam("url") String url,
                            @RequestParam("powerId") Integer powerId,
                            HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=utf-8");
        if (url == null || "".equals(url)) {
            writeResult(response, RespStatusEnum.FAIL.getValue(), "权限路径不能为空", null);
            return;
        }

        if (!RegExUtil.match("^[0-9]{1,4}$", powerId.toString())) {
            writeResult(response, RespStatusEnum.FAIL.getValue(), "权限必须是1-4位数字", null);
            return;
        }
        Resources resources = new Resources();
        resources.setUrl(url);
        resources.setPowerId(powerId);
        resources.setCtime(TimeUtil.getNow());
        resources.setUtime(TimeUtil.getNow());
        ResourcesService.AddResourcesStuates result = resourcesService.addResources(resources);
        writeResult(response,result.getStatusEnum().getValue(),result.getMsg(),null);


    }
}
