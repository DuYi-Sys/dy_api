package com.duyi.management.dao;

import com.duyi.management.domain.User;
import java.util.List;

public interface UserDao {

    void add(User admin);

    void update(User admin);

    void updatePassword(User admin);

    User findByAccount(String account);

    User findByEmail(String email);

    List<User> findAll();

}
