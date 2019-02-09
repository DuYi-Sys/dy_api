package com.duyi.datatransfer;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DataTransferRegister {

    private static Map<Class<? extends DataTansferAble>, DataTransfer> map = new HashMap<>();

    @Autowired
    private  List<DataTransfer> list;

    @PostConstruct
    public void init() throws Exception {
        System.out.println("list长度为：" + list.size());
        for(DataTransfer dataTransfer : list) {
            System.out.println(dataTransfer.getClass().getName());
            DataTransferDesc dataTransferDesc = dataTransfer.getClass().getAnnotation(DataTransferDesc.class);
            if (dataTransferDesc == null) {
                throw new Exception("error DataTransferDesc is null, class name:" + dataTransfer.getClass().getName());
            }
            map.put(dataTransferDesc.tableType(), dataTransfer);
        }
        System.out.println(JSON.toJSONString(map));
    }

    public static DataTransfer getDataTransfer(Class<? extends DataTansferAble> clazz) {
        return map.get(clazz);
    }
}
