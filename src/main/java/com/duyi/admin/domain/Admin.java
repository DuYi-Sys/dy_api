package com.duyi.admin.domain;

import java.util.Objects;

public class Admin {
    private int id;
    private String account;
    private String name;
    private String password;
    private String email;
    private int ctime;
    private int utime;

    public Admin() {
    }

    public Admin(int id, String account, String name, String password, String email, int ctime, int utime) {
        this.id = id;
        this.account = account;
        this.name = name;
        this.password = password;
        this.email = email;
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
        Admin admin = (Admin) object;
        return account == admin.account;
    }

    @Override
    public int hashCode() {
        return Objects.hash(account);
    }
}