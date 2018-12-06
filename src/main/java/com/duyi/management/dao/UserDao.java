package com.duyi.management.dao;

import com.duyi.management.domain.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {

    void add(User admin);

    void update(User admin);

    void updatePassword(User admin);

    User queryByAppkey(@Param("appkey") String appkey);

    User findByAccount(@Param("account") String account);

    User findByEmail(@Param("email") String email);

    List<User> findAll();

}
