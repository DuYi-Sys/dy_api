package com.duyi.management.controller;

import com.duyi.common.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.servlet.ServletRequest;

@Controller
public class MainController extends BaseController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String indexPage(ServletRequest servletRequest) throws Exception {

        return "redirect:/management/index";
    }

}
