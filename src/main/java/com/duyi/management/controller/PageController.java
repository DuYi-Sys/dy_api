package com.duyi.management.controller;

import com.duyi.common.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/management")
public class PageController extends BaseController {

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView indexPage() throws Exception {

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
