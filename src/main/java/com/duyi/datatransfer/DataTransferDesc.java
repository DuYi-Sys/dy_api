package com.duyi.datatransfer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataTransferDesc {
    String tableName();
    DataTransferUtil.DataActType DATA_ACT_TYPE();
}
