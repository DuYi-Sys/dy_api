package com.duyi.management.controller;

import com.duyi.common.BaseController;
import com.duyi.management.domain.User;
import com.duyi.management.service.UserService;
import com.duyi.util.RSAEncrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/management")
public class PageController extends BaseController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView indexPage(Model model, HttpServletRequest servletRequest) throws Exception {

        Cookie[] cookies = servletRequest.getCookies();

        String encodeSno = null;
        for (Cookie cookie : cookies) {
            if ("uid".equals(cookie.getName())) {
                try {
                    encodeSno = RSAEncrypt.decrypt(cookie.getValue());
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        User user = userService.findByAccount(encodeSno);

        Map<String, Object> result = new HashMap<>();
        result.put("user", user);
        model.addAllAttributes(result);

        return new ModelAndView("/index");
    }

    @RequestMapping(value = "/stu", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView stuPage() throws Exception {

        return new ModelAndView("/stu");
    }

    @RequestMapping(value = "/mt", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView mtPage() throws Exception {

        return new ModelAndView("/mt");
    }

}
