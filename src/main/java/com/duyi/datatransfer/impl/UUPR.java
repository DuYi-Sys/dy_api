package com.duyi.datatransfer.impl;

import com.duyi.admin.domain.Admin;
import com.duyi.datatransfer.DataTransfer;
import com.duyi.datatransfer.DataTransferDesc;
import org.springframework.stereotype.Component;


@Component
@DataTransferDesc(tableType = Admin.class)
public class UUPR implements DataTransfer<Admin> {

    @Override
    public void create(Admin originData, Admin nowData) {
        System.out.println("创建了一个admin");
    }

    @Override
    public void update(Admin originData, Admin nowData) {
        System.out.println("修改了一个admin");
    }

    @Override
    public void delete(Admin originData, Admin nowData) {

    }
}
