package com.duyi.meituan.dao;

import com.duyi.meituan.domain.MtUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MtUserDao {

    void add(MtUser mtUser);

    MtUser queryByAppkeyAndName(@Param("appkey") String appkey, @Param("userName") String userName);

}
