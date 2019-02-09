package com.duyi.datatransfer;

public class DataTransferUtil {
    public static <T extends DataTansferAble> void act(Class<T> clazz, DataActType dataActType, T originData, T nowData ){
        DataTransfer dataTransfer = DataTransferRegister.getDataTransfer(clazz);
        if (dataTransfer != null) {
            switch (dataActType) {
                case CREATE: dataTransfer.create(originData, nowData);break;
                case UPDATE: dataTransfer.update(originData, nowData);break;
                case DELETE: dataTransfer.delete(originData, nowData);break;
            }
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
