package com.duyi.datatransfer.aop;

import com.duyi.datatransfer.DataTansferAble;

public class EntityConstructor implements DomainConstructor {
    @Override
    public DataTansferAble getOriginData(Object[] objects) {
        return null;
    }

    @Override
    public DataTansferAble getNowData(Object[] objects) {
        return null;
    }

    @Override
    public Class getDomainType(Object[] objects) {
        return null;
    }
}
