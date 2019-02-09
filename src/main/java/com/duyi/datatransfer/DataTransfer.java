package com.duyi.datatransfer;

public interface DataTransfer<T extends DataTansferAble> {

    void create(T originData, T nowData);
    void update(T originData, T nowData);
    void delete(T originData, T nowData);

}
