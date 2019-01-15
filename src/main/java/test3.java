import com.alibaba.fastjson.JSONObject;
import com.duyi.tuling.controller.TulingController;
import com.duyi.tuling.service.TulingService;
import com.duyi.util.TimeUtil;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class test3 {
    public static void main(String[] args) throws Exception {
//        TulingService ts = new TulingService();
//        String rs = ts.createConnection("你好");
//        JSONObject json1 = JSONObject.parseObject(rs);
//        String rs1 = json1.getString("results");
//        String rs2 = rs1.substring(1,rs1.length() - 1);
//        System.out.println(rs);
//        JSONObject json2 = JSONObject.parseObject(rs2);
//        String str2 = json2.getString("values");
//        JSONObject json = JSONObject.parseObject(str2);
//        System.out.println(str2);
//        Calendar calendar = Calendar.getInstance();
////        System.out.println(calendar);
//        int year = calendar.get(Calendar.YEAR);
//        System.out.println(year);
//        long millis = calendar.getTimeInMillis();
//
//        System.out.println(millis);
//        int month = calendar.get(Calendar.MONTH);
//        System.out.println(month);
//
//        int date = calendar.get(Calendar.DATE);
//        System.out.println(date);

//        int pre = TimeUtil.getPreDate(10);
//        System.out.println(pre);

        System.out.println(URLDecoder.decode("%E5%BC%A0%E4%B8%89"));

    }


}
