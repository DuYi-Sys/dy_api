package com.duyi.datatransfer.aop;

import com.duyi.datatransfer.DataTansferAble;
import org.springframework.stereotype.Component;


@Component
public class EmptyConstructor implements DomainConstructor {

    @Override
    public DataTansferAble getOriginData(Object[] objects) {

        return (DataTansferAble)objects[0];
    }

    @Override
    public DataTansferAble getNowData(Object[] objects) {
        return (DataTansferAble)objects[0];
    }

    @Override
    public Class getDomainType(Object[] objects) {
        return objects[0].getClass();
    }
}
