package com.duyi.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

    public static String getDateString(int pre) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis() - pre * 86400000);
        String month = calendar.get(Calendar.MONTH) + 1 < 10 ? "0" + (calendar.get(Calendar.MONTH) + 1) : "" + (calendar.get(Calendar.MONTH) + 1);
        String day = calendar.get(Calendar.DAY_OF_MONTH) < 10 ? "0" + calendar.get(Calendar.DAY_OF_MONTH) : "" + calendar.get(Calendar.DAY_OF_MONTH);
        return "" + calendar.get(Calendar.YEAR) + "-" + month + "-" + day;
    }

    public static int getNow() {

        Calendar calendar = Calendar.getInstance();
        long nowTime = calendar.getTimeInMillis();
        return (int)(nowTime / 1000);

    }

    public static long getNowTime() {
        return System.currentTimeMillis();
    }

    public static String getDateTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateTime = sdf.format(date);
        return dateTime;
    }

    public static int getTodyDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.DATE);
    }

    public static int getPreDate(int pre,MomentEnum momentEnum) {
        Calendar calendar = momentEnum.getCalender();
        return (int)((calendar.getTimeInMillis() - pre * 86400000) /1000);
    }

    public enum MomentEnum {
        FIRST_SECOND(0),NOW_SECOND(1),LAST_SECOND(100);

        int value;
        MomentEnum(int value) {
            this.value = value;
        }

        public Calendar getCalender(){
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, Math.min(calendar.get(Calendar.HOUR_OF_DAY) * this.value, 23));
            calendar.set(Calendar.MINUTE, Math.min(calendar.get(Calendar.MINUTE) * this.value, 59));
            calendar.set(Calendar.SECOND, Math.min(calendar.get(Calendar.SECOND) * this.value, 59));
            return calendar;
        }
    }
}
