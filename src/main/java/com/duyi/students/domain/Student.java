package com.duyi.students.domain;

import java.util.Date;
import java.util.Objects;

public class Student {

    private int id;
    private String sNo;
    private String name;
    private String email;
    private int sex;
    private int birth;
    private String phone;
    private String address;
    private String appkey;
    private int ctime;
    private int utime;


    public Student() {
    }

    public Student(int id, String sNo, String name, String email, int sex, int birth, String phone, String address, String appkey, int ctime, int utime) {
        this.id = id;
        this.sNo = sNo;
        this.name = name;
        this.email = email;
        this.sex = sex;
        this.birth = birth;
        this.phone = phone;
        this.address = address;
        this.appkey = appkey;
        this.ctime = ctime;
        this.utime = utime;
    }
//    public int getPage() { return page; }
//
//    public void setPage(int page) { this.page = page; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getsNo() {
        return sNo;
    }

    public void setsNo(String sNo) {
        this.sNo = sNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getBirth() {
        return birth;
    }

    public void setBirth(int birth) {
        this.birth = birth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id &&
                Objects.equals(sNo, student.sNo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sNo);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", sNo='" + sNo + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", sex=" + sex +
                ", birth=" + birth +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", appkey='" + appkey + '\'' +
                ", ctime=" + ctime +
                ", utime=" + utime +
                '}';
    }
}
