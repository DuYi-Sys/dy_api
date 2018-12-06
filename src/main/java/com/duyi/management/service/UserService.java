package com.duyi.management.service;

import com.duyi.management.dao.UserDao;
import com.duyi.management.domain.User;
import com.duyi.util.MD5Util;
import com.duyi.util.MailOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public boolean addUser(String account, String password, String email, String appkey, int ctime, int utime) throws NoSuchAlgorithmException {

        User user = userDao.findByAccount(account);

        if (user != null) {

            return false;

        } else {

            user = new User();

            user.setAccount(account);

            user.setPassword(MD5Util.MD5Encode(password, "utf8"));

            user.setEmail(email);

            user.setAppkey(appkey);

            user.setCtime(ctime);

            user.setUtime(utime);

            try {

                userDao.add(user);

            } catch (Exception e) {

                return false;

            }

            return true;
        }
    }

    public User login(String account, String password) {

        User user = userDao.findByAccount(account);

        String md5Password = MD5Util.MD5Encode(password, "utf8");

        if (user != null && user.getPassword().equals(md5Password)) {

            return user;

        } else {

            return null;

        }
    }

    public User queryById(long id) {
        return userDao.queryById(id);
    }

    public User queryByAppkey(String appkey) {
        return userDao.queryByAppkey(appkey);
    }

    public User findByEmail(String email) {

        return userDao.findByEmail(email);

    }

    public User findByAccount(String account) {

        return userDao.findByAccount(account);

    }

    public void senEmail(String encryptionAccount, String user, String password, String host, String from, String to, String subject) throws Exception {

        //邮箱内容
        StringBuffer sb = new StringBuffer();


        MailOperation operation = new MailOperation();

        String yzm = "http://127.0.0.1:8080/userActivate?encryptionAccount=" + encryptionAccount;

        sb.append("<!DOCTYPE>" + "<div bgcolor='#f1fcfa'   style='border:1px solid #d9f4ee; font-size:14px; line-height:22px; color:#005aa0;padding-left:1px;padding-top:5px;   padding-bottom:5px;'><span style='font-weight:bold;'>温馨提示：</span>"
                + "<div style='width:950px;font-family:arial;'>欢迎使用渡一教育平台，您的激活链接为：<br/><h2 style='color:green'><a href=" + yzm + ">" + yzm + "</a></h2><br/>本邮件由系统自动发出，请勿回复。<br/>感谢您的使用。<br/>XXXX有限公司</div>"
                + "</div>");


        String res = operation.sendMail(user, password, host, from, to,
                subject, sb.toString());

        System.out.println(res);


    }

    public void senForgetEmail(String urlEncryptionAccount, String user, String password, String host, String from, String to, String subject) throws Exception {

        //邮箱内容
        StringBuffer sb = new StringBuffer();


        MailOperation operation = new MailOperation();

        String yzm = "http://127.0.0.1:8080/resetPasswords.html?urlEncryptionAccount=" + urlEncryptionAccount;
        System.out.println("ras:" + urlEncryptionAccount);

        sb.append("<!DOCTYPE>" + "<div bgcolor='#f1fcfa'   style='border:1px solid #d9f4ee; font-size:14px; line-height:22px; color:#005aa0;padding-left:1px;padding-top:5px;   padding-bottom:5px;'><span style='font-weight:bold;'>温馨提示：</span>"
                + "<div style='width:950px;font-family:arial;'>欢迎使用渡一教育平台，您的修改密码连接为：<br/><h2 style='color:green'><a href=" + yzm + ">" + yzm + "</a></h2><br/>本邮件由系统自动发出，请勿回复。<br/>感谢您的使用。<br/>XXXX有限公司</div>"
                + "</div>");


        String res = operation.sendMail(user, password, host, from, to,
                subject, sb.toString());

        System.out.println(res);


    }

    public boolean updateStatus(String account) {

        User user = userDao.findByAccount(account);

        if (user == null) {

            return false;

        } else {

            user.setStatus(1);

            userDao.update(user);

            return true;

        }

    }

    public boolean updatePassword(String account, String newPassword) {

        User user = userDao.findByAccount(account);

        String md5Password = MD5Util.MD5Encode(newPassword, "utf8");

        if (user == null) {

            return false;

        } else {

            user.setPassword(md5Password);

            userDao.updatePassword(user);

            return true;

        }

    }

}
