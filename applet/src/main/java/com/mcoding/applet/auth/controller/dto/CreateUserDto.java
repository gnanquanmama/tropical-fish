package com.mcoding.applet.auth.controller.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author wzt on 2020/3/25.
 * @version 1.0
 */
@Data
public class CreateUserDto {

    @ApiModelProperty(value = "微信jscode")
    @NotNull(message = "微信jscode不能为空")
    private String jsCode;

    @ApiModelProperty(value = "经销商ID")
    @NotNull(message = "经销商ID不能为空")
    private String dealerId;

    @ApiModelProperty(value = "经销商编码")
    @NotNull(message = "经销商编码不能为空")
    private String dealerCode;

    @ApiModelProperty(value = "经销商名称")
    private String dealerName;

    @ApiModelProperty(value = "手机号码")
    private String mobileNumber;

    @NotNull(message = "昵称不能为空")
    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "头像")
    private String avatarUrl;

    @ApiModelProperty(value = "性别 0：未知、1：男、2：女")
    private Integer gender;

    @ApiModelProperty(value = "省份")
    private String province;

    @ApiModelProperty(value = "城市")
    private String city;

    @ApiModelProperty(value = "区域")
    private String country;

}
