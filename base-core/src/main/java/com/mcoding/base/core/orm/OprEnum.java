package com.mcoding.base.core.orm;

public enum OprEnum {


    EQ("eq");

    private String value;

    OprEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

}
