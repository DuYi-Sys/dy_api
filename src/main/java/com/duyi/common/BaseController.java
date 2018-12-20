package com.duyi.common;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BaseController {

    public void writeJsonp(HttpServletResponse resp, String result) throws IOException {
        resp.getWriter().write( result);
    }

    public void writeJsonp(HttpServletResponse resp, String callback, String result) throws IOException {
        resp.getWriter().write(callback + "(" + result + ")");
    }

    public void writeResult(HttpServletResponse resp, String status, String msg, Object data) throws IOException {

        JSONObject result = new JSONObject();

        result.put("status", status);
        result.put("msg", msg);
        result.put("data", data);

        resp.getWriter().write(result.toJSONString());
    }

    public void writeResult(HttpServletResponse resp, String status, String msg, String userName) throws IOException {

        JSONObject result = new JSONObject();

        result.put("status", status);
        result.put("msg", msg);
        result.put("userName", userName);

        resp.getWriter().write(result.toJSONString());
    }

    public void writeResult(HttpServletResponse resp, String status, int count, Object data) throws IOException {

        JSONObject result = new JSONObject();

        result.put("status", status);
        result.put("count", count);
        result.put("findBypageList", data);

        resp.getWriter().write(result.toJSONString());
    }
}
