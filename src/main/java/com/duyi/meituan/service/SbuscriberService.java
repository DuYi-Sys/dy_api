package com.duyi.meituan.service;

import com.duyi.students.dao.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SbuscriberService {
    @Autowired
    StudentDao studentDao;

    public boolean addSbucriberService(String account,String name,String password,String email,int born) {

        return false;
    }
}
