package com.duyi.meituan.service;

import com.duyi.common.RespStatusEnum;
import com.duyi.meituan.dao.MtUserDao;
import com.duyi.meituan.domain.MtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MtUserService {

    @Autowired
    MtUserDao mtUserDao;

    public MtUserStatusEnum addUser(MtUser mtUser) {
        try {
            mtUserDao.add(mtUser);
            return MtUserStatusEnum.SUCCESS;
        } catch (Exception e) {
            MtUser tempUser = mtUserDao.queryByAppkeyAndName(mtUser.getAppkey(), mtUser.getUserName());
            if (tempUser != null) {
                return MtUserStatusEnum.EXIST_USER_NAME;
            } else {
                return MtUserStatusEnum.UNKNOW_ERROR;
            }
        }
    }

    public MtLoginStatusEnum checkLogin(String appkey, String userName, String password) {

        MtUser mtUser = mtUserDao.queryByAppkeyAndName(appkey, userName);

        if (mtUser == null) {
            return MtLoginStatusEnum.NOT_FOUND_USERNAME;
        }

        if (password.equals(mtUser.getPassword())) {
            return MtLoginStatusEnum.SUCCESS;
        } else {
            return MtLoginStatusEnum.PASSWORD_ERROR;
        }

    }

    public enum MtLoginStatusEnum {

        NOT_FOUND_USERNAME("找不到该用户", RespStatusEnum.FAIL), PASSWORD_ERROR("密码错误", RespStatusEnum.FAIL), SUCCESS("登录成功", RespStatusEnum.SUCCESS);

        private RespStatusEnum statusEnum;
        private String msg;

        MtLoginStatusEnum(String msg, RespStatusEnum statusEnum) {
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


    public enum MtUserStatusEnum {

        EXIST_USER_NAME("用户名已存在", RespStatusEnum.FAIL), SUCCESS("注册成功", RespStatusEnum.SUCCESS), UNKNOW_ERROR("未知错误", RespStatusEnum.FAIL);

        private RespStatusEnum statusEnum;
        private String msg;

        MtUserStatusEnum (String msg, RespStatusEnum statusEnum) {

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
