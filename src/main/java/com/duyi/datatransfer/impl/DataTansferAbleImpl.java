package com.duyi.datatransfer.impl;

import com.duyi.datatransfer.DataTansferAble;

public class DataTansferAbleImpl implements DataTansferAble{
    public String msg;

    public DataTansferAbleImpl() {}

    public DataTansferAbleImpl(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
