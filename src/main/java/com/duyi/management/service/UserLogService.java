package com.duyi.management.service;

import com.duyi.common.RespStatusEnum;
import com.duyi.management.dao.UserDao;
import com.duyi.management.domain.User;
import com.duyi.util.MD5Util;
import com.duyi.util.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserLogService {
    @Autowired
    UserDao userDao;

    /**
     * 用户登录
     * @param account
     * @param password
     * @return
     */
    public UserLoginStatusEnum login(String account, String password) {

        User stUser = userDao.findByAccount(account);

        String md5Password = MD5Util.MD5Encode(password, "utf8");

        if (stUser == null) {
            return UserLoginStatusEnum.NOT_FOUND_USERNAME;
        }

        if(md5Password.equals(stUser.getPassword())){
            return UserLoginStatusEnum.SUCCESS;
        } else {
            return UserLoginStatusEnum.PASSWORD_ERROR;
        }

    }

    /**
     * 发送激活邮件
     * @param encryptionAccount
     * @param to
     * @param subject
     * @throws Exception
     */
    public void sendAcctiveEmail(String encryptionAccount, String to, String subject) throws Exception {

        //邮箱内容
        StringBuffer sb = new StringBuffer();

//        String yzm = "http://api.duyiedu.com/userActivate?encryptionAccount=" + encryptionAccount;
        String yzm = "http://127.0.0.1:8080/userActivate?encryptionAccount=" + encryptionAccount;

        sb.append("<!DOCTYPE>" + "<div bgcolor='#f1fcfa'   style='border:1px solid #d9f4ee; font-size:14px; line-height:22px; color:#005aa0;padding-left:1px;padding-top:5px;   padding-bottom:5px;'><span style='font-weight:bold;'>温馨提示：</span>"
                + "<div style='width:950px;font-family:arial;'>欢迎使用渡一教育平台，您的激活链接为：<br/><h2 style='color:green'><a href=" + yzm + ">" + yzm + "</a></h2><br/>本邮件由系统自动发出，请勿回复。<br/>感谢您的使用。<br/>XXXX有限公司</div>"
                + "</div>");


        MailUtil.sendMail(to, subject, yzm);
    }

    /**
     * 添加一个用户
     * @param user
     * @return
     */
    public UserStatusEnum addUser(User user){

        try {
            userDao.add(user);
            return UserStatusEnum.SUCCESS;
        } catch (Exception e) {
            User user1 = userDao.findByAccount(user.getAccount());
            if(user != null) {
                return UserStatusEnum.EXIST_USER_NAME;
            } else {
                return UserStatusEnum.UNKNOW_ERROR;
            }

        }

    }

    /**
     * 激活用户
     * @param account
     * @return
     */
    public UserActivateStatusEnum updateStatus(String account) {

        User user = userDao.findByAccount(account);

        try {
            user.setStatus(1);
            userDao.update(user);
            return UserActivateStatusEnum.SUCCESS;
        } catch (Exception e) {
            if(user == null) {
                return UserActivateStatusEnum.NOT_FOND_USER;
            }else {
                return UserActivateStatusEnum.UNKNOW_ERROR;
            }
        }

    }

    /**
     * 查找一个用户
     * @param account
     * @return
     */
    public User findByAccount(String account) {

        return userDao.findByAccount(account);
    }



    public enum UserLoginStatusEnum {
        NOT_FOUND_USERNAME("找不到用户名", RespStatusEnum.FAIL), PASSWORD_ERROR("密码错误", RespStatusEnum.FAIL), SUCCESS("登录成功", RespStatusEnum.SUCCESS);

        private RespStatusEnum statusEnum;
        private String msg;

        UserLoginStatusEnum(String msg, RespStatusEnum statusEnum) {
            this.msg = msg;
            this.statusEnum = statusEnum;
        }

        public RespStatusEnum getStatusEnum() {
            return statusEnum;
        }

        public String getMsg() {
            return msg;
        }
    }

    /**
     * 确认Appkey存在
     * @param appkey
     * @return
     */
    public User queryByAppkey(String appkey) {
        return userDao.queryByAppkey(appkey);
    }

    /**
     * 显示全部User
     * @return
     */
    public List<User> findAll() {
        try{
            List<User> users = userDao.findAll();
            return users;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public enum UserStatusEnum {

        EXIST_USER_NAME(RespStatusEnum.FAIL, "用户名已存在"), SUCCESS(RespStatusEnum.SUCCESS, "添加成功"), UNKNOW_ERROR(RespStatusEnum.FAIL, "未知错误");

        private RespStatusEnum statusEnum;
        private String msg;

        UserStatusEnum(RespStatusEnum statusEnum, String msg) {
            this.statusEnum = statusEnum;
            this.msg = msg;
        }

        public RespStatusEnum getStatusEnum() {
            return statusEnum;
        }

        public String getMsg() {
            return msg;
        }
    }


    public enum UserActivateStatusEnum {

        NOT_FOND_USER(RespStatusEnum.FAIL, "用户不存在"), SUCCESS(RespStatusEnum.SUCCESS, "激活成功"), UNKNOW_ERROR(RespStatusEnum.FAIL, "未知错误");

        private RespStatusEnum statusEnum;
        private String msg;

        UserActivateStatusEnum(RespStatusEnum statusEnum, String msg) {
            this.statusEnum = statusEnum;
            this.msg = msg;
        }

        public RespStatusEnum getStatusEnum() {
            return statusEnum;
        }

        public String getMsg() {
            return msg;
        }
    }
}
