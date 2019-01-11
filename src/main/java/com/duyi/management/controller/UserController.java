package com.duyi.management.controller;


import com.duyi.common.BaseController;
import com.duyi.common.RespStatusEnum;
import com.duyi.management.domain.User;
import com.duyi.management.service.UserService;
import com.duyi.util.MD5Util;
import com.duyi.util.RSAEncrypt;
import com.duyi.util.RegExUtil;
import com.duyi.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.midi.Soundbank;
import java.net.URLEncoder;

@Controller
public class UserController extends BaseController {
    @Autowired
    UserService userService;

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

        UserService.UserLoginStatusEnum result =  userService.login(account, password);

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

        UserService.UserStatusEnum result =  userService.addUser(user);
        writeResult(resp,result.getStatusEnum().getValue(),result.getMsg(),null);

        if (result.getStatusEnum() == RespStatusEnum.SUCCESS) {

            //发送激活邮件
            String to = email;// 收件人
            String subject = "渡一用户激活";
//            String encryptionAccount = URLEncoder.encode(RSAEncrypt.encrypt(account));
//            userService.sendAcctiveEmail(encryptionAccount, to, subject);
            userService.sendAcctiveEmail(account, to, subject);
//            writeResult(resp,RespStatusEnum.SUCCESS.getValue(),"Please open your registered email for activation!",null);

        }


    }

    @RequestMapping(value = "/userActivate", method = RequestMethod.GET)
    @ResponseBody
    public void adminActivate(@RequestParam("encryptionAccount") String encryptionAccount,
                              HttpServletRequest req,
                              HttpServletResponse resp) throws Exception {

        resp.setContentType("text/html;charset=utf-8");
        String encodeAccount = RSAEncrypt.decrypt(encryptionAccount);
        UserService.UserActivateStatusEnum result = userService.updateStatus(encodeAccount);
//        writeResult(resp,result.getStatusEnum().getValue(),result.getMsg(),null);
        System.out.println(result.getStatusEnum());
        if (result.getStatusEnum() == RespStatusEnum.SUCCESS) {
//           req.getRequestDispatcher("/activate.html").forward(req,resp);
            resp.sendRedirect("/activate.html");
        }
    }

    @RequestMapping(value = "/sendEmail", method = RequestMethod.GET)
    @ResponseBody
    public void sendEmail(@RequestParam("account") String account,
                          @RequestParam("email") String email,
                          HttpServletResponse resp) throws Exception {
        resp.setContentType("text/html;charset=utf-8");
        String to = email;// 收件人
        String subject = "渡一用户激活";
        userService.sendAcctiveEmail(account, to, subject);
    }

    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
    @ResponseBody
    public void getUserInfo( @CookieValue(name = "uid") String uid,
                            HttpServletResponse resp) throws Exception {
        resp.setContentType("text/html;charset=utf-8");
        String account = RSAEncrypt.decrypt(uid);
        User user = userService.findByAccount(account);
        writeResult(resp,RespStatusEnum.SUCCESS.getValue(),"查询成功",user);
    }



}
