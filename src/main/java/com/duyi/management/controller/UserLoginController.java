package com.duyi.management.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.duyi.common.BaseController;
import com.duyi.common.RespStatusEnum;
import com.duyi.management.domain.User;
import com.duyi.management.service.UserLogService;
import com.duyi.students.domain.RespModel;
import com.duyi.util.MD5Util;
import com.duyi.util.RSAEncrypt;
import com.duyi.util.RegExUtil;
import com.duyi.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

@Controller
public class UserLoginController extends BaseController {
    @Autowired
    UserLogService userLogService;

    /**
     * 用户登录接口
     * @param account 账号
     * @param password 密码
     * @param resp
     * @throws Exception
     */
    @RequestMapping(value = "/userLogin",method = RequestMethod.POST)
    @ResponseBody
    public void userLogin(@RequestParam("account") String account,
                          @RequestParam("password") String password,
                          HttpServletResponse resp) throws Exception {

        resp.setContentType("text/html;charset=utf-8");

        if (!RegExUtil.match("^[a-zA-Z0-9_]{4,16}$", account)) {
            writeResult(resp, RespStatusEnum.FAIL.getValue(), "用户名必须为4-16位的字母数字下划线组成", null);
            return;
        }

        if (!RegExUtil.match("^[a-zA-Z0-9_]{6,16}$", password)) {
            writeResult(resp, RespStatusEnum.FAIL.getValue(), "密码必须为6-16位的字母数字下划线组成", null);
            return;
        }

        UserLogService.UserLoginStatusEnum result =  userLogService.login(account, password);

        if (result.getStatusEnum() == RespStatusEnum.SUCCESS) {

            String str = RSAEncrypt.encrypt(account);
            Cookie cookie = new Cookie("uid", str);
            resp.addCookie(cookie);
        }
        writeResult(resp,result.getStatusEnum().getValue(),result.getMsg(),null);

    }

    /**
     * 用户注册
     * @param account 账号
     * @param password 密码
     * @param rePassword 确认密码
     * @param email 邮箱
     * @param resp
     * @throws Exception
     */

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public void registe(@RequestParam("account") String account,
                        @RequestParam("password") String password,
                        @RequestParam("rePassword") String rePassword,
                        @RequestParam("email") String email,
                        HttpServletResponse resp) throws Exception {

        resp.setContentType("text/html;charset=utf-8");
        System.out.println(account + password + rePassword + email);

        if (!RegExUtil.match("^[a-zA-Z0-9_]{4,16}$", account)) {
            writeResult(resp, RespStatusEnum.FAIL.getValue(), "用户名必须为4-16位的字母数字下划线组成", null);
            return;
        }

        if (!RegExUtil.match("^[a-zA-Z0-9_]{6,16}$", password)) {
            writeResult(resp, RespStatusEnum.FAIL.getValue(), "密码必须为6-16位的字母数字下划线组成", null);
            return;
        }

        if(!rePassword.equals(password)) {
            writeResult(resp, RespStatusEnum.FAIL.getValue(), "两次密码输入不一致", null);
            return;
        }

        if(!RegExUtil.match("^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,5}$",email)) {
            writeResult(resp, RespStatusEnum.FAIL.getValue(), "邮箱格式不正确", null);
            return;
        }

        String appkey = account + "_" + TimeUtil.getNowTime();

        User user = new User();
        user.setAccount(account);
        user.setAccount(account);
        user.setPassword(MD5Util.MD5Encode(password, "utf8"));
        user.setEmail(email);
        user.setAppkey(appkey);
        user.setCtime(TimeUtil.getNow());
        user.setUtime(TimeUtil.getNow());

        UserLogService.UserStatusEnum result =  userLogService.addUser(user);
        writeResult(resp,result.getStatusEnum().getValue(),result.getMsg(),null);

        if (result.getStatusEnum() == RespStatusEnum.SUCCESS) {

            //发送激活邮件
            String to = email;// 收件人
            String subject = "渡一用户激活";
            String encryptionAccount = URLEncoder.encode(RSAEncrypt.encrypt(account));
            userLogService.sendAcctiveEmail(encryptionAccount, to, subject);
//            writeResult(resp,RespStatusEnum.SUCCESS.getValue(),"Please open your registered email for activation!",null);

        }

    }

    @RequestMapping(value = "/userActivate", method = RequestMethod.GET)
    @ResponseBody
    public void adminActivate(String encryptionAccount, HttpServletRequest res, HttpServletResponse resp) throws Exception {

        resp.setContentType("text/html;charset=utf-8");
        String encodeAccount = RSAEncrypt.decrypt(encryptionAccount);
        UserLogService.UserActivateStatusEnum result = userLogService.updateStatus(encodeAccount);
        writeResult(resp,result.getStatusEnum().getValue(),result.getMsg(),null);

    }

}
