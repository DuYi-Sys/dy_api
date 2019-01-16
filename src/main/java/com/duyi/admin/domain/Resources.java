package com.duyi.admin.domain;

import java.util.Objects;

public class Resources {

    private int id;
    private String url;
    private int powerId;
    private int sign;
    private int ctime;
    private int utime;

    public Resources() {
    }

    public Resources(int id, String url, int powerId, int sign, int ctime, int utime) {
        this.id = id;
        this.url = url;
        this.powerId = powerId;
        this.sign = sign;
        this.ctime = ctime;
        this.utime = utime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPowerId() {
        return powerId;
    }

    public void setPowerId(int powerId) {
        this.powerId = powerId;
    }

    public int getSign() {
        return sign;
    }

    public void setSign(int sign) {
        this.sign = sign;
    }

    public int getCtime() {
        return ctime;
    }

    public void setCtime(int ctime) {
        this.ctime = ctime;
    }

    public int getUtime() {
        return utime;
    }

    public void setUtime(int utime) {
        this.utime = utime;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Resources resources = (Resources) object;
        return powerId == resources.powerId &&
                Objects.equals(url, resources.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, powerId);
    }
}
