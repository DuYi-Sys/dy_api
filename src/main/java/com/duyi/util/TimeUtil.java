package com.duyi.util;

import java.util.Calendar;

public class TimeUtil {


    public static int getNow() {

        Calendar calendar = Calendar.getInstance();
        long nowTime = calendar.getTimeInMillis();
        return (int)(nowTime / 1000);

    }

}
