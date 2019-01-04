package com.duyi.statistics.domain;

import java.util.Objects;

public class Count {
    private String appkey;
    private String path;
    private int pv ;
    private String date;
    private int ctime;

    public Count() {
    }

    public Count(String appkey, String path, int pv, String date, int ctime) {
        this.appkey = appkey;
        this.path = path;
        this.pv = pv;
        this.date = date;
        this.ctime = ctime;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getPv() {
        return pv;
    }

    public void setPv(int pv) {
        this.pv = pv;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCtime() {
        return ctime;
    }

    public void setCtime(int ctime) {
        this.ctime = ctime;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Count count = (Count) object;
        return Objects.equals(appkey, count.appkey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(appkey);
    }

    @Override
    public String toString() {
        return "Count{" +
                "appkey='" + appkey + '\'' +
                ", path='" + path + '\'' +
                ", count=" + pv +
                ", date=" + date +
                ", ctime=" + ctime +
                '}';
    }
}
