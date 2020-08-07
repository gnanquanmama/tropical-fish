package com.mcoding.applet.auth.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wzt on 2020/3/30.
 * @version 1.0
 */
@Data
public class BindingStoreDto {

    @ApiModelProperty("门店ID")
    private String storeId;

    @ApiModelProperty("门店编码")
    private String storeCode;

    @ApiModelProperty("门店名称")
    private String storeName;

    @ApiModelProperty("联系人")
    private String contactMan;

    @ApiModelProperty("手机号码")
    private String mobileNumber;

}
