package com.mcoding.base.rest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author wzt on 2020/3/30.
 * @version 1.0
 */
@ApiModel("布尔对象")
@Data
@AllArgsConstructor
public class BoolObject {

    @ApiModelProperty("1为是，0为否")
    private int bool;

}
