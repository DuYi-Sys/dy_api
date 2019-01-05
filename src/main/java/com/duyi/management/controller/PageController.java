package com.duyi.management.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.duyi.common.BaseController;
import com.duyi.common.RespStatusEnum;
import com.duyi.management.domain.User;
import com.duyi.management.service.UserLogService;
import com.duyi.management.service.UserService;
import com.duyi.statistics.dao.CountDao;
import com.duyi.statistics.domain.Count;
import com.duyi.statistics.domain.DateSum;
import com.duyi.statistics.service.StatisticsService;
import com.duyi.util.RSAEncrypt;
import com.duyi.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Key;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/management")
public class PageController extends BaseController {

    @Autowired
//    UserService userService;
    UserLogService userLogService;
    @Autowired
    StatisticsService statisticsService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView indexPage(Model model,
                                  @CookieValue(name = "uid") String uid,
                                  HttpServletRequest servletRequest) throws Exception {

//        Cookie[] cookies = servletRequest.getCookies();
//
//        String encodeSno = null;
//        for (Cookie cookie : cookies) {
//            if ("uid".equals(cookie.getName())) {
//                try {
//                    encodeSno = RSAEncrypt.decrypt(cookie.getValue());
//                    break;
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }

        String encodeSno = RSAEncrypt.decrypt(uid);
        User user = userLogService.findByAccount(encodeSno);
        String appkey = user.getAppkey();
        Integer sum1 = statisticsService.getPvCount(appkey,TimeUtil.getPreDate(0, TimeUtil.MomentEnum.FIRST_SECOND),TimeUtil.getPreDate(0, TimeUtil.MomentEnum.LAST_SECOND));
        Integer sum3 = statisticsService.getPvCount(appkey,TimeUtil.getPreDate(2, TimeUtil.MomentEnum.FIRST_SECOND),TimeUtil.getPreDate(0, TimeUtil.MomentEnum.LAST_SECOND));
        Integer sum7 = statisticsService.getPvCount(appkey,TimeUtil.getPreDate(6, TimeUtil.MomentEnum.FIRST_SECOND),TimeUtil.getPreDate(0, TimeUtil.MomentEnum.LAST_SECOND));
        Integer sumTotal = statisticsService.getPvCount(appkey,TimeUtil.getPreDate(10000, TimeUtil.MomentEnum.FIRST_SECOND),TimeUtil.getPreDate(0, TimeUtil.MomentEnum.LAST_SECOND));
        Map<String, Object> result = new HashMap<>();
//        result.put("sum1", sum1);
//        result.put("sum3", sum3);
//        result.put("sum7", sum7);
//        result.put("sumTotal", sumTotal);
        isEmpty(result,"sum1", sum1);
        isEmpty(result,"sum3", sum3);
        isEmpty(result,"sum7", sum7);
        isEmpty(result,"sumTotal", sumTotal);
        result.put("user", user);
        model.addAllAttributes(result);

        return new ModelAndView("/index");
    }

    public void isEmpty(Map<String,Object> result, String key, Integer value){
        if(value == null) {
            result.put(key,0);
        } else {
            result.put(key,value);
        }
    }



    @RequestMapping(value = "/stu", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView stuPage(Model model,
                                @CookieValue(name = "uid") String uid) throws Exception {

        String encodeSno = RSAEncrypt.decrypt(uid);
        User user = userLogService.findByAccount(encodeSno);
        Map<String, Object> result = new HashMap<>();
        result.put("user", user);
        model.addAllAttributes(result);
        return new ModelAndView("/stu");
    }

    @RequestMapping(value = "/mt", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView mtPage(Model model,
                               @CookieValue(name = "uid") String uid) throws Exception {

        String encodeSno = RSAEncrypt.decrypt(uid);
        User user = userLogService.findByAccount(encodeSno);
        Map<String, Object> result = new HashMap<>();
        result.put("user", user);
        model.addAllAttributes(result);
        return new ModelAndView("/mt");
    }

    /**
     *
     * @param model
     * @param offset 偏移量
     * @param resp
     * @return
     * @throws IOException
     */
    @RequestMapping("/getSumRecode")
    public ModelAndView getSumRecode(Model model,
                                     @CookieValue(name = "uid") String uid,
                                     @RequestParam("offset") int offset,
                                     HttpServletResponse resp) throws Exception {
//        if(appkey == null || "".equals(appkey)) {
//            writeResult(resp, RespStatusEnum.FAIL.getValue(), "appkey不能为空", null);
//            return null;
//        }
        String account = RSAEncrypt.decrypt(uid);
        User user = userLogService.findByAccount(account);
        String appkey = user.getAppkey();
        int begin = TimeUtil.getPreDate(offset, TimeUtil.MomentEnum.FIRST_SECOND);
        int end = TimeUtil.getPreDate(0, TimeUtil.MomentEnum.LAST_SECOND);
        int sum = statisticsService.getPvCount(appkey,begin,end);
        model.addAttribute("sum",sum);
        System.out.println(sum);
        return new ModelAndView("/index");
    }


    @RequestMapping("/getDayCount")
    public void getDayCount(Model model,
                                     @CookieValue(name = "uid") String uid,
                                     HttpServletResponse resp) throws Exception {
        String account = RSAEncrypt.decrypt(uid);
        User user = userLogService.findByAccount(account);
        String appkey = user.getAppkey();
        List<DateSum> countList = statisticsService.getDayCount(appkey, TimeUtil.getPreDate(6, TimeUtil.MomentEnum.FIRST_SECOND), TimeUtil.getPreDate(0, TimeUtil.MomentEnum.LAST_SECOND));
        JSONArray result = new JSONArray();
        Map<String, JSONObject> tempMap = new HashMap();
        for (DateSum dateSum : countList) {
            JSONObject temp = new JSONObject();
            //将结果封装到json中
            temp.put("date",dateSum.getDate());
            temp.put("sum",dateSum.getSum());
            tempMap.put(dateSum.getDate(), temp);
        }
        for (int i = 0 ; i < 7 ; i ++) {
            String temp = TimeUtil.getDateString(i);
            JSONObject json = tempMap.get(temp);
            if (json == null) {
                json = new JSONObject();
                json.put("date", temp);
                json.put("sum", 0);
            }
            result.add(json);
        }
        writeResult(resp, "success", "成功", result);
    }


}
