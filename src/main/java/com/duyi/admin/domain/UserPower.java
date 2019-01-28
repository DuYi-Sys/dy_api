package com.duyi.admin.domain;

import java.util.Objects;

public class UserPower {
    private int id;
    private String account;
    private int powerId;
    private String ctime;
    private String utime;

    public UserPower() {
    }

    public UserPower(int id, String account, int powerId, String ctime, String utime) {
        this.id = id;
        this.account = account;
        this.powerId = powerId;
        this.ctime = ctime;
        this.utime = utime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getPowerId() {
        return powerId;
    }

    public void setPowerId(int powerId) {
        this.powerId = powerId;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getUtime() {
        return utime;
    }

    public void setUtime(String utime) {
        this.utime = utime;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        UserPower userPower = (UserPower) object;
        return Objects.equals(account, userPower.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(account);
    }
}
