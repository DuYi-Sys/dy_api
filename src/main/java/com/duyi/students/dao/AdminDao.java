package com.duyi.students.dao;

import com.duyi.students.domain.Admin;

import java.util.List;

public interface AdminDao {

    void add(Admin admin);

    void update(Admin admin);

    void updatePassword(Admin admin);

    Admin findByAccount(String account);

    Admin findByEmail(String email);

    List<Admin> findAll();

}
