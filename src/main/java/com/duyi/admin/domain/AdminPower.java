package com.duyi.admin.domain;

import java.util.Objects;

public class AdminPower {
    private String account;
    private int power_id;
    private String url;
    private int sign;

    public AdminPower() {
    }

    public AdminPower(String account, int powerId, String url, int sign) {
        this.account = account;
        this.power_id = powerId;
        this.url = url;
        this.sign = sign;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getPower_id() {
        return power_id;
    }

    public void setPower_id(int power_id) {
        this.power_id = power_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSign() {
        return sign;
    }

    public void setSign(int sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "AdminPower{" +
                "account='" + account + '\'' +
                ", powerId=" + power_id +
                ", url='" + url + '\'' +
                ", sign=" + sign +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        AdminPower that = (AdminPower) object;
        return power_id == that.power_id &&
                Objects.equals(account, that.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account, power_id);
    }
}
