package com.duyi.meituan.domain;

import java.util.Date;
import java.util.Objects;

public class Sbuscriber {
    private int id;
    private String account;
    private String name;
    private String password;
    private String email;
    private String phone;
    private String born;
    private Date ctime;
    private Date utime;

    public Sbuscriber() {
    }

    public Sbuscriber(int id, String account, String name, String password, String email, String phone, String born, Date ctime, Date utime) {
        this.id = id;
        this.account = account;
        this.name = name;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.born = born;
        this.ctime = ctime;
        this.utime = utime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBorn() {
        return born;
    }

    public void setBorn(String born) {
        this.born = born;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Date getUtime() {
        return utime;
    }

    public void setUtime(Date utime) {
        this.utime = utime;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sbuscriber that = (Sbuscriber) o;
        return id == that.id &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Sbuscriber{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", born='" + born + '\'' +
                ", ctime=" + ctime +
                ", utime=" + utime +
                '}';
    }


}
