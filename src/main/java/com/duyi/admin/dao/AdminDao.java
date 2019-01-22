package com.duyi.admin.dao;

import com.duyi.admin.domain.Admin;
import com.duyi.admin.domain.AdminPower;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminDao {
    void addAdmin(Admin admin);
    Admin queryByAccount(@Param("account") String account);
    Admin queryByEmail(@Param("email") String email);
    List<AdminPower> queryAll();
}
