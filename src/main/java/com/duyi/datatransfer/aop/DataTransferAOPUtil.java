package com.duyi.datatransfer.aop;

import com.duyi.datatransfer.DataTansferAble;
import com.duyi.datatransfer.DataTransferUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Aspect
public class DataTransferAOPUtil {

    private static Map<Class<? extends DomainConstructor>, DomainConstructor> map = new HashMap<>();

    @Autowired
    private List<DomainConstructor> constructors;

    @PostConstruct
    public void initDataTransferAOP() {
        for (DomainConstructor domainConstructor : constructors) {
            map.put(domainConstructor.getClass(), domainConstructor);
        }
    }

    @Pointcut("execution(* com.duyi..*Dao.*(..))")
    private void pointCut(){};

    @Around("pointCut()")
    private void checkSecurity(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        DataTransferAOP dataTransferAOP = signature.getMethod().getDeclaredAnnotation(DataTransferAOP.class);
        if (dataTransferAOP != null) {
            DomainConstructor domainConstructor =  map.get(dataTransferAOP.DOMAIN_CONSTRUCTOR());
            DataTansferAble originData = domainConstructor.getOriginData(joinPoint.getArgs());
            try {
                ((ProceedingJoinPoint) joinPoint).proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            DataTansferAble nowData = domainConstructor.getNowData(joinPoint.getArgs());
            DataTransferUtil.act(domainConstructor.getDomainType(joinPoint.getArgs()), dataTransferAOP.DATA_ACT_TYPE(), originData, nowData);
        }
    }

}