package com.duyi.datatransfer.aop;

import com.duyi.datatransfer.DataTansferAble;

public interface DomainConstructor<T extends DataTansferAble, S> {

    public T getOriginData(S... s);

    public T getNowData(S... s);

    public Class<T> getDomainType(S... s);

}
