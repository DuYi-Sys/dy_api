package com.duyi.admin.dao;

import com.duyi.admin.domain.Admin;
import org.apache.ibatis.annotations.Param;

public interface AdminDao {
    void addAdmin(Admin admin);
    Admin queryByAccount(@Param("account") String account);
    Admin queryByEmail(@Param("email") String email);
}
