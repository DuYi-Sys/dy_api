package com.duyi.datatransfer.aop;

import com.duyi.datatransfer.DataTransferUtil;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataTransferAOP {
    DataTransferUtil.DataActType DATA_ACT_TYPE();
    Class<? extends DomainConstructor> DOMAIN_CONSTRUCTOR() default EmptyConstructor.class;
}
