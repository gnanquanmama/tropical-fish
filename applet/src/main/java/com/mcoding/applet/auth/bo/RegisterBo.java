package com.mcoding.applet.auth.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author wzt on 2020/3/25.
 * @version 1.0
 */
@ApiModel("注册用户信息")
@Data
public class RegisterBo implements Serializable {

    @ApiModelProperty("服务端分配token")
    private String token;

    @ApiModelProperty("sessionKey")
    private String sessionKey;

    @ApiModelProperty("用户ID")
    private Integer userId;

    @ApiModelProperty("用户昵称")
    private String nickName;

    @ApiModelProperty("门店ID")
    private String storeId;

    @ApiModelProperty("门店编码")
    private String storeCode;

    @ApiModelProperty("门店名称")
    private String storeName;

    @ApiModelProperty("经销商ID")
    private Integer dealerId;

    @ApiModelProperty("经销商编码")
    private String dealerCode;

    @ApiModelProperty("经销商名称")
    private String dealerName;

}
