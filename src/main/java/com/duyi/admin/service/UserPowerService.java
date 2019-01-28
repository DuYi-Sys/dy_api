package com.duyi.admin.service;

import com.duyi.admin.dao.UserPowerDao;
import com.duyi.admin.domain.UserPower;
import com.duyi.common.RespStatusEnum;
import com.duyi.util.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserPowerService {
    @Autowired
    UserPowerDao userPowerDao;

    public UserPowerService.UserPowerStatues addUserPower(UserPower userPower) {
        try{
            userPowerDao.addPower(userPower);
            return UserPowerStatues.SUCCESS;
        } catch (Exception e) {
            UserPower u = userPowerDao.queryByAccountAndPowerId(userPower.getAccount(),userPower.getPowerId());
            if (u != null) {
                return UserPowerStatues.EXIST_POWER;
            } else {
                e.printStackTrace();
                return UserPowerStatues.UNKNOWN_ERROR;
            }
        }
    }

    public boolean queryUserPower(String account,int powerId) {
        UserPower userPower = userPowerDao.queryByAccountAndPowerId(account,powerId);
        if(userPower != null) {
            return true;
        }
        return false;
    }



    public void sendEmail(String account, String power, String to, String subject) {

        //邮箱内容
        StringBuffer sb = new StringBuffer();
//        String yzm = "http://api.duyiedu.com/userActivate?encryptionAccount=" + encryptionAccount;
//        String yzm = "http://127.0.0.1:8080/adminLogin.html";
        sb.append("<!DOCTYPE>" + "<div bgcolor='#f1fcfa'   style='border:1px solid #d9f4ee; font-size:14px; line-height:22px; color:#005aa0;padding-left:1px;padding-top:5px;   padding-bottom:5px;'><span style='font-weight:bold;'>温馨提示：</span>"
                + "<div style='width:950px;font-family:arial;'>欢迎使用渡一教育后台管理系统！<br/> 您的帐号为：'" + account + "' , 权限为:'" + power + "'<br/>本邮件由系统自动发出，请勿回复。<br/>感谢您的使用。<br/>黑龙江渡一信息技术开发有限公司</div>"
                + "</div>");
        MailUtil.sendMail(to, subject, sb.toString());
    }
    public enum  UserPowerStatues {
        EXIST_POWER(RespStatusEnum.FAIL, "该管理员已有此权限"), SUCCESS(RespStatusEnum.SUCCESS, "添加成功"), UNKNOWN_ERROR(RespStatusEnum.FAIL,"未知错误");

        private RespStatusEnum statusEnum;
        private String msg;

        UserPowerStatues(RespStatusEnum statusEnum, String msg) {
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
