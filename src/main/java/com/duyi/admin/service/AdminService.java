package com.duyi.admin.service;

import com.duyi.admin.dao.AdminDao;
import com.duyi.admin.domain.Admin;
import com.duyi.admin.domain.AdminPower;
import com.duyi.common.RespStatusEnum;
import com.duyi.datatransfer.DataTransfer;
import com.duyi.datatransfer.DataTransferUtil;
import com.duyi.util.MD5Util;
import com.duyi.util.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    AdminDao adminDao;

    /**
     * 添加一个管理员
     *
     * @param admin
     */
    public AdminService.addAdminStatusEnum addAdmin(Admin admin) {
        try {
            adminDao.addAdmin(admin);
            //添加权限
//            userPowerService.addUserPower(admin.getAccount(),powerId);
//            DataTransferUtil.act(Admin.class, DataTransferUtil.DataActType.CREATE, null, admin);
            return addAdminStatusEnum.SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();
            Admin admin1 = adminDao.queryByAccount(admin.getAccount());
            if (admin1 != null) {
                return addAdminStatusEnum.EXIST_USER_ACCOUNT;
            } else {
                e.printStackTrace();
                return addAdminStatusEnum.UNKNOW_ERROR;
            }
        }
    }

    public Admin queryByAccount(String account) {
        return adminDao.queryByAccount(account);
    }

    public List<AdminPower> queryAll() { return adminDao.queryAll(); }

    public AdminService.LoginStatusEnum login(String account, String password) {
        Admin admin = adminDao.queryByAccount(account);
        String md5Password = MD5Util.MD5Encode(password, "utf-8");

        if (admin == null) {
            return LoginStatusEnum.NOT_FOND_ADMIN;
        }

        if (md5Password.equals(admin.getPassword())) {
            return LoginStatusEnum.SUCCESS;
        } else {
            return LoginStatusEnum.UNKNOW_ERROR;
        }
    }

    public boolean checkEmail(String email) {
        Admin admin = adminDao.queryByEmail(email);
        if (admin != null) {
            return true;
        }
        return false;
    }

    public void sendEmail(String account, String password, String to, String subject) {

        //邮箱内容
        StringBuffer sb = new StringBuffer();
//        String yzm = "http://api.duyiedu.com/userActivate?encryptionAccount=" + encryptionAccount;
        String yzm = "http://127.0.0.1:8080/adminLogin.html";
        sb.append("<!DOCTYPE>" + "<div bgcolor='#f1fcfa'   style='border:1px solid #d9f4ee; font-size:14px; line-height:22px; color:#005aa0;padding-left:1px;padding-top:5px;   padding-bottom:5px;'><span style='font-weight:bold;'>温馨提示：</span>"
                + "<div style='width:950px;font-family:arial;'>欢迎使用渡一教育后台管理系统！<br/> 您的帐号为：'" + account + "' , 密码为:'" + password + "'<br/><h2 style='color:green'><a href=" + yzm + ">点击进入后台管理系统</a></h2><br/>本邮件由系统自动发出，请勿回复。<br/>感谢您的使用。<br/>黑龙江渡一信息技术开发有限公司</div>"
                + "</div>");
        MailUtil.sendMail(to, subject, sb.toString());
    }

    public enum addAdminStatusEnum {

        EXIST_USER_ACCOUNT(RespStatusEnum.FAIL, "此管理员已存在"), SUCCESS(RespStatusEnum.SUCCESS, "添加成功"), UNKNOW_ERROR(RespStatusEnum.FAIL, "未知错误");

        private RespStatusEnum statusEnum;
        private String msg;

        addAdminStatusEnum(RespStatusEnum statusEnum, String msg) {
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

    public enum LoginStatusEnum {

        NOT_FOND_ADMIN(RespStatusEnum.FAIL, "账号错误"), SUCCESS(RespStatusEnum.SUCCESS, "登录成功"), UNKNOW_ERROR(RespStatusEnum.FAIL, "未知错误"), PASSWORD_ERRO(RespStatusEnum.FAIL, "密码错误");

        private RespStatusEnum statusEnum;
        private String msg;

        LoginStatusEnum(RespStatusEnum statusEnum, String msg) {
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
