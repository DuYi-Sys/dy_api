package com.duyi.datatransfer;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Component

public class DataTransferRegister {
    public static Map<String, DataTransfer> map = new HashMap<>();
//    @Autowired
    private  List<DataTransfer> list;
//    @PostConstruct
    public void init() throws Exception {
        System.out.println(list.size());
        for(DataTransfer d : list) {
            System.out.println(d.getClass().getName());
            Class clazz = d.getClass();
            DataTransferDesc desc = (DataTransferDesc) clazz.getAnnotation(DataTransferDesc.class);
            if (desc == null ) {
                throw new Exception("抛异常拉~~~~");
            }
            map.put(desc.tableName() + "-" + desc.DATA_ACT_TYPE().getValue(),d);
        }
        System.out.println(JSON.toJSONString(map));
    }
}
