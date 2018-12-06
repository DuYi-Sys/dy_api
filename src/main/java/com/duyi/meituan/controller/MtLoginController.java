package com.duyi.meituan.controller;

import com.duyi.common.BaseController;
import com.duyi.common.RespStatusEnum;
import com.duyi.meituan.domain.MtUser;
import com.duyi.meituan.service.MtUserService;
import com.duyi.util.RegExUtil;
import com.duyi.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping(value = "/api/meituan")
public class MtLoginController extends BaseController {

    @Autowired
    MtUserService mtUserService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public void login(@RequestParam(name = "appkey") String appkey,
                      @RequestParam(name = "userName") String userName,
                      @RequestParam(name = "password") String password,
                      HttpServletResponse resp) throws IOException {

        if (!RegExUtil.match("^[a-zA-Z0-9_]{4,16}$", userName)) {
            writeResult(resp, RespStatusEnum.FAIL.getValue(), "用户名必须为4-16位的字母数字下划线组成", null);
        }

        if (!RegExUtil.match("^[a-zA-Z0-9_]{6,16}$", password)) {
            writeResult(resp, RespStatusEnum.FAIL.getValue(), "用户名必须为6-16位的字母数字下划线组成", null);
        }

        MtUserService.MtLoginStatusEnum result = mtUserService.checkLogin(appkey, userName, password);

        writeResult(resp, result.getStatusEnum().getValue(), result.getMsg(), null);

    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public void register(@RequestParam(name = "appkey") String appkey,
                         @RequestParam(name = "userName") String userName,
                         @RequestParam(name = "password") String password,
                         @RequestParam(name = "repeatPassword") String repeatPassword,
                         HttpServletResponse resp) throws IOException {

        if (!RegExUtil.match("^[a-zA-Z0-9_]{4,16}$", userName)) {
            writeResult(resp, RespStatusEnum.FAIL.getValue(), "用户名必须为4-16位的字母数字下划线组成", null);
        }

        if (!RegExUtil.match("^[a-zA-Z0-9_]{6,16}$", password)) {
            writeResult(resp, RespStatusEnum.FAIL.getValue(), "用户名必须为6-16位的字母数字下划线组成", null);
        }

        if (!RegExUtil.match("^[a-zA-Z0-9_]{6,16}$", repeatPassword)) {
            writeResult(resp, RespStatusEnum.FAIL.getValue(), "用户名必须为6-16位的字母数字下划线组成", null);
        }

        if (!password.equals(repeatPassword)) {
            writeResult(resp, RespStatusEnum.FAIL.getValue(), "两次输入的用户名不一致", null);
        }

        MtUser mtUser = new MtUser();
        mtUser.setUserName(userName);
        mtUser.setPassword(password);
        mtUser.setAppkey(appkey);
        mtUser.setCtime(TimeUtil.getNow());
        mtUser.setUtime(TimeUtil.getNow());

        MtUserService.MtUserStatusEnum result = mtUserService.addUser(mtUser);

        writeResult(resp, result.getStatusEnum().getValue(), result.getMsg(), null);

    }
}
