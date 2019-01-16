package com.duyi.admin.dao;

import com.duyi.admin.domain.UserPower;
import org.apache.ibatis.annotations.Param;

public interface UserPowerDao {
    void addPower(UserPower userPower);
    UserPower queryByAccountAndPowerId (@Param("account") String account, @Param("powerId") int powerId);
}
