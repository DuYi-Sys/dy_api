package com.duyi.admin.dao;

import com.duyi.admin.domain.Admin;
import com.duyi.admin.domain.AdminPower;
import com.duyi.datatransfer.aop.DataTransferAOP;
import com.duyi.datatransfer.DataTransferUtil;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminDao {

//    @DataTransferAOP(DATA_ACT_TYPE = DataTransferUtil.DataActType.CREATE)
    void addAdmin(Admin admin);

    @DataTransferAOP(DATA_ACT_TYPE = DataTransferUtil.DataActType.READ)
    Admin queryByAccount(@Param("account") String account);

    Admin queryByEmail(@Param("email") String email);
    List<AdminPower> queryAll();
}
