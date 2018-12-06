//package com.duyi.management.controller;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.duyi.common.BaseController;
//import com.duyi.management.domain.User;
//import com.duyi.management.service.UserService;
//import com.duyi.students.domain.RespModel;
//import com.duyi.students.enums.RespStatusEnum;
//import com.duyi.util.RSAEncrypt;
//import com.duyi.util.TimeUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.net.URLEncoder;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class UserController extends BaseController {
//
//    @Autowired
//    UserService userService;
//
//    @RequestMapping(value = "/login",method = RequestMethod.POST)
//    @ResponseBody
//    public void login( String account, String password, HttpServletResponse resp) throws Exception {
//
//        String resultString = "";
//
//        JSONObject result = new JSONObject();
//
//        if (account == null || account.equals("")) {
//
//            resultString = JSON.toJSONString(
//                    new RespModel(
//                            RespStatusEnum.FAIL.getValue(),
//                            "The account is null",
//                            null));
//            writeJsonp(resp,resultString);
//            return;
//        }
//        if (password == null || password.equals("")) {
//
//            resultString = JSON.toJSONString(
//                    new RespModel(
//                            RespStatusEnum.FAIL.getValue(),
//                            "The password is null",
//                            null));
//            writeJsonp(resp,resultString);
//            return;
//        }
//
//        User user = userService.login(account, password);
//
//        if (user == null) {
//
//            resultString = JSON.toJSONString(
//                    new RespModel(
//                            RespStatusEnum.FAIL.getValue(),
//                            "account or password error",
//                            null));
//
//            writeJsonp(resp,resultString);
//
//        } else {
//
//            String str = RSAEncrypt.encrypt(account);
//
//            Cookie cookie = new Cookie("account", str);
//
//            resp.addCookie(cookie);
//
//            resultString = JSON.toJSONString(new RespModel(RespStatusEnum.SUCCESS.getValue(),null, null));
//
//            writeJsonp(resp,resultString);
//        }
//        resp.getWriter().close();
//
//    }
//
//
//
//    /**
//     * 用户注册
//     * @param account 账号
//     * @param password 密码
//     * @param rePassword 确认密码
//     * @param email 邮箱
//     * @param resp
//     * @throws Exception
//     */
//    @RequestMapping(value = "/register", method = RequestMethod.POST)
//
//    public void registe(@RequestParam("account") String account, @RequestParam("password") String password, @RequestParam("rePassword") String rePassword, @RequestParam("email") String email, HttpServletResponse resp) throws Exception {
//
//        String resultString = "";
//
//        System.out.println(account + password + rePassword + email);
//        if (account == null || account.equals("")) {
//
//            resultString = JSON.toJSONString(
//                    new RespModel(
//                            RespStatusEnum.FAIL.getValue(),
//                            "The account is null",
//                            null));
//            writeJsonp(resp,resultString);
//
//            return;
//        }
//
//        if (password == null || password.equals("")) {
//
//            resultString = JSON.toJSONString(
//                    new RespModel(
//                            RespStatusEnum.FAIL.getValue(),
//                            "The password is null",
//                            null));
//            writeJsonp(resp,resultString);
//
//            return;
//        }
//
//        if (rePassword == null || rePassword.equals("")) {
//
//            resultString = JSON.toJSONString(
//                    new RespModel(
//                            RespStatusEnum.FAIL.getValue(),
//                            "The rePassword count null",
//                            null));
//            writeJsonp(resp,resultString);
//
//            return;
//
//        }
//
//        if (!password.equals(rePassword)) {
//
//            resultString = JSON.toJSONString(
//                    new RespModel(
//                            RespStatusEnum.FAIL.getValue(),
//                            "The two input passwords do not match",
//                            null));
//
//            writeJsonp(resp,resultString);
//
//            return;
//
//        }
//
//        if (email == null || email.equals("")) {
//
//            resultString = JSON.toJSONString(
//                    new RespModel(
//                            RespStatusEnum.FAIL.getValue(),
//                            "The email count null",
//                            null));
//
//            writeJsonp(resp,resultString);
//
//            return;
//        }
//
//        // 邮箱验证规则
//        String regEx = "^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,5}$";
//
//        // 编译正则表达式
//        Pattern pattern = Pattern.compile(regEx);
//
//        // 忽略大小写的写法
//        // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
//
//        Matcher matcher = pattern.matcher(email);
//
//        if (!matcher.matches()) {
//
//            resultString = JSON.toJSONString(
//                    new RespModel(
//                            RespStatusEnum.FAIL.getValue(),
//                            "The Email format error",
//                            null));
//
//            writeJsonp(resp,resultString);
//
//            return;
//
//        }
//
//        boolean isTrue = userService.addUser(account, password, email, "", TimeUtil.getNow(), TimeUtil.getNow());
//
//        if (isTrue) {
//
//            String user = "18846411586@163.com";
//
//            String password1 = "ywywenyu22";
//
//            String host = "smtp.163.com";
//
//            String from = "18846411586@163.com";
//
//            String to = email;// 收件人
//
//            String subject = "输入邮件主题";
//
//            String encryptionAccount = URLEncoder.encode(RSAEncrypt.encrypt(account));
//
//            userService.senEmail(encryptionAccount, user, password1, host, from, to, subject);
//
//            resultString = JSON.toJSONString(
//                    new RespModel(
//                            RespStatusEnum.SUCCESS.getValue(),
//                            "Please open your registered email for activation!",
//                            null));
//
//            writeJsonp(resp,resultString);
//
//
//        } else {
//
//            resultString = JSON.toJSONString(
//                    new RespModel(
//                            RespStatusEnum.FAIL.getValue(),
//                            "The admin is exist",
//                            null));
//
//            writeJsonp(resp,resultString);
//
//        }
//    }
//
//    @RequestMapping(value = "/act", method = RequestMethod.POST)
//    @ResponseBody
//    public void activateHref(@RequestParam(name = "email") String email, HttpServletRequest req, HttpServletResponse resp) throws Exception {
//
//        String resultString = "";
//
//        // 邮箱验证规则
//        String regEx = "^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,5}$";
//
//        // 编译正则表达式
//        Pattern pattern = Pattern.compile(regEx);
//
//        // 忽略大小写的写法
//        // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
//
//        Matcher matcher = pattern.matcher(email);
//
//        // 字符串是否与正则表达式相匹配
//        if (matcher.matches()) {
//
//            System.out.println("格式正确");
//
//            //发送邮件
//
//            String user = "18846411586@163.com";
//
//            String password = "ywywenyu22";
//
//            String host = "smtp.163.com";
//
//            String from = "18846411586@163.com";
//
//            String to = email;// 收件人
//
//            String subject = "输入邮件主题";
//
//
//            User dyUser = userService.findByEmail(email);
//
////            判断是否存在这个人
//
//            if (dyUser == null) {
//
//                resultString = JSON.toJSONString(
//                        new RespModel(
//                                RespStatusEnum.FAIL.getValue(),
//                                "not find this user",
//                                null));
//
//                writeJsonp(resp,resultString);
//
//                return;
//
//            }
//
//            String encryptionAccount = RSAEncrypt.encrypt(dyUser.getAccount());
//
//            String urlEncryptionAccount = URLEncoder.encode(encryptionAccount, "utf-8");
//
//            userService.senEmail(urlEncryptionAccount, user, password, host, from, to, subject);
//
//            resultString = JSON.toJSONString(
//                    new RespModel(
//                            RespStatusEnum.SUCCESS.getValue(),
//                            null,
//                            null ));
//        } else {
//
//
//            resultString = JSON.toJSONString(
//                    new RespModel(
//                            RespStatusEnum.FAIL.getValue(),
//                            "Mail format error",
//                            null));
//
//            writeJsonp(resp,resultString);
//
//        }
//
//    }
//
//    @RequestMapping(value = "/sendForgetEmail", method = RequestMethod.POST)
//    @ResponseBody
//    public void sendForgetEmail(@RequestParam(name = "email") String email, HttpServletResponse resp) throws Exception {
//
//
//        String resultString = "";
////        setHeader(resp);
//
//        // 邮箱验证规则
//        String regEx = "^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,5}$";
//
//        // 编译正则表达式
//        Pattern pattern = Pattern.compile(regEx);
//
//        // 忽略大小写的写法
//        // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
//
//        Matcher matcher = pattern.matcher(email);
//
//        // 字符串是否与正则表达式相匹配
//        if (matcher.matches()) {
//
//            System.out.println("格式正确");
//
//            //发送邮件
//
//            String user = "18846411586@163.com";
//
//            String password = "ywywenyu22";
//
//            String host = "smtp.163.com";
//
//            String from = "18846411586@163.com";
//
//            String to = email;// 收件人
//
//            String subject = "输入邮件主题";
//
//
//            User dyUser = userService.findByEmail(email);
//
////            判断是否存在这个人
//
//            if (dyUser == null) {
//
//                resultString = JSON.toJSONString(
//                        new RespModel(
//                                RespStatusEnum.FAIL.getValue(),
//                                "not find this user",
//                                null));
//
//                writeJsonp(resp,resultString);
//
//                return;
//            }
//
//            String encryptionAccount = RSAEncrypt.encrypt(dyUser.getAccount());
//
//            String urlEncryptionAccount = URLEncoder.encode(encryptionAccount, "utf-8");
//
//            userService.senForgetEmail(urlEncryptionAccount, user, password, host, from, to, subject);
//
//
//            resultString = JSON.toJSONString(
//                    new RespModel(
//                            RespStatusEnum.SUCCESS.getValue(),
//                            "Please open your registered email for activation!",
//                            null));
//
//            writeJsonp(resp,resultString);
//
//        } else {
//
//            resultString = JSON.toJSONString(
//                    new RespModel(
//                            RespStatusEnum.FAIL.getValue(),
//                            "Mail format error",
//                            null));
//
//            writeJsonp(resp,resultString);
//
//        }
//
//    }
//
//
//    @RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)
//    public void forgotPassword(@RequestParam(name = "newPassword") String newPassword,
//                               @RequestParam(name = "cofPassword") String cofPassword,
//                               @RequestParam(name = "urlEncryptionAccount") String urlEncryptionAccount, HttpServletResponse resp) throws Exception {
//
//        String resultString = "";
//
//        if (newPassword == null || newPassword.equals("")) {
//
//            resultString = JSON.toJSONString(
//                    new RespModel(
//                            RespStatusEnum.FAIL.getValue(),
//                            "The newPassword is null",
//                            null));
//
//            writeJsonp(resp,resultString);
//
//            return;
//
//        }
//
//        if (cofPassword == null || cofPassword.equals("")) {
//
//            resultString = JSON.toJSONString(
//                    new RespModel(
//                            RespStatusEnum.FAIL.getValue(),
//                            "The cofPassword is null",
//                            null));
//
//            writeJsonp(resp,resultString);
//
//            return;
//
//        }
//
//        if (!newPassword.equals(cofPassword)) {
//
//            resultString = JSON.toJSONString(
//                    new RespModel(
//                            RespStatusEnum.FAIL.getValue(),
//                            "The two input passwords do not match",
//                            null));
//
//            writeJsonp(resp,resultString);
//
//            return;
//
//        }
//
//        String encodeAccount = RSAEncrypt.decrypt(urlEncryptionAccount);
//
//        //修改密码语句
//
//        boolean isTrue = userService.updatePassword(encodeAccount, newPassword);
//
//        if (isTrue) {
//
//            resultString = JSON.toJSONString(new RespModel(RespStatusEnum.SUCCESS.getValue(),null,null));
//
//            writeJsonp(resp,resultString);
//
//
//        } else {
//
//            resultString = JSON.toJSONString(
//                    new RespModel(
//                            RespStatusEnum.FAIL.getValue(),
//                            "not find this user",
//                            null));
//
//            writeJsonp(resp,resultString);
//
//        }
//
//
//    }
//}
