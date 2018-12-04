package com.duyi.meituan.dao;

import com.duyi.meituan.domain.Sbuscriber;

import java.util.List;

public interface SbuscriberDao {
    void addUser(Sbuscriber sbuscriber);
    void delUser(String account);
    void updateUser(Sbuscriber sbuscriber);
    Sbuscriber findByAccount(String account);
    Sbuscriber findByEmail(String Email);
    List<Sbuscriber> findAll();
}
