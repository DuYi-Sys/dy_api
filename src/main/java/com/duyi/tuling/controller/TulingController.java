package com.duyi.tuling.controller;

import com.alibaba.fastjson.JSONObject;
import com.duyi.common.BaseController;
import com.duyi.common.RespStatusEnum;
import com.duyi.tuling.service.TulingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping(value = "/api/tuling")
public class TulingController extends BaseController {
    @Autowired
    TulingService tulingService;
    @RequestMapping(value = "/sendMsg",method = RequestMethod.GET)
    public void sendMsg(@RequestParam("appkey") String appkey,
                        @RequestParam("msg") String msg,
                        HttpServletResponse resp) throws IOException {
        String rs = tulingService.createConnection(msg);
        JSONObject json1 = JSONObject.parseObject(rs);
        String rs1 = json1.getString("results");
        String rs2 = rs1.substring(1,rs1.length() - 1);
        JSONObject json2 = JSONObject.parseObject(rs2);
        String str2 = json2.getString("values");
        JSONObject json = JSONObject.parseObject(str2);
        writeResult(resp,RespStatusEnum.SUCCESS.getValue(),"对话成功",json);
    }
}
