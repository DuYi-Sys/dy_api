package com.duyi.datatransfer;

public class DataTransferUtil {
    public static void act(String tableName, DataActType dataActType, Object originData, Object nowData ){
        DataTransfer dataTransfer = DataTransferRegister.map.get(tableName+"-"+dataActType.getValue());
        if (dataTransfer != null) {
            dataTransfer.execute(originData,nowData);
        }
    }


    public  enum DataActType{
        CREATE("CREATE"),DELETE("DELETE"),UPDATE("UPDATE"),READ("READ");
        private String value;
        DataActType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}
