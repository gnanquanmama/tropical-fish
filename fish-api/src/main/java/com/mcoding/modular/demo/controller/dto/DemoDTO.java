package com.mcoding.modular.demo.controller.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@ApiModel("demo实体")
@Data
public class DemoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("类型（1：团购，2:用户，3：设置）")
    @NotNull(message = "日志类型不能为空")
    private Integer type;

    @ApiModelProperty("日志内容")
    @NotNull(message = "日志内容不能为空")
    @Size(max = 1024, message = "日志内容长度不能超过1024个字符")
    private String content;

    @ApiModelProperty("创建人")
    @TableField("operationUser")
    private String operationUser;

    @ApiModelProperty("创建时间")
    @TableField("operationTime")
    private Date operationTime;

}