package com.duyi.datatransfer.impl;

import com.duyi.datatransfer.DataTransfer;
import org.springframework.stereotype.Component;

@Component
public class upr implements DataTransfer {
    @Override
    public void execute(Object originData, Object nowData) {
        System.out.println("------");
    }
}
