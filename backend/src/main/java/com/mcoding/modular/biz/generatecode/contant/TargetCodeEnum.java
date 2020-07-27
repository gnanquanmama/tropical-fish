package com.mcoding.modular.biz.generatecode.contant;

/**
 * @author wzt on 2020/2/9.
 * @version 1.0
 */
public enum TargetCodeEnum {

    BIG_PACKAGE_ACTIVITY_ORDER("BIG-PACKAGE-ACTIVITY-ORDER", "大套餐-活动订单编码");


    private String targetCode;
    private String desc;

    TargetCodeEnum(String targetCode, String desc) {
        this.targetCode = targetCode;
        this.desc = desc;
    }

    public String getTargetCode() {
        return targetCode;
    }

    public String getDesc() {
        return desc;
    }
}
