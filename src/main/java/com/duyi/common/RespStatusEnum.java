package com.duyi.common;

public enum  RespStatusEnum {

    SUCCESS("success"), FAIL("fail");

    private String value;

    RespStatusEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
