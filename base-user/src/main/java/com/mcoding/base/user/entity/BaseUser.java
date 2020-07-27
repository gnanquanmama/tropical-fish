package com.mcoding.base.user.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.util.Date;
import java.io.Serializable;

import com.mcoding.base.common.util.excel.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 基础用户
 * </p>
 *
 * @author wzt
 * @since 2020-06-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("base_user")
@ApiModel(value="BaseUser", description="基础用户")
public class BaseUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Excel(title = "openId")
    @ApiModelProperty(value = "openId")
    @TableField("openId")
    private String openId;

    @ApiModelProperty(value = "unionId")
    @TableField("unionId")
    private String unionId;

    @Excel(title = "手机号码")
    @ApiModelProperty(value = "手机号码")
    @TableField("mobile_number")
    private String mobileNumber;

    @Excel(title = "昵称")
    @ApiModelProperty(value = "昵称")
    @TableField("nick_name")
    private String nickName;

    @ApiModelProperty(value = "用户名称")
    @TableField("user_name")
    private String userName;

    @ApiModelProperty(value = "用户状态，1为正常，2为冻结")
    @TableField("user_status")
    private Integer userStatus;

    @ApiModelProperty(value = "头像")
    @TableField("avatar_url")
    private String avatarUrl;

    @ApiModelProperty(value = "性别 0：未知、1：男、2：女")
    @TableField("gender")
    private Integer gender;

    @ApiModelProperty(value = "省份")
    @TableField("province")
    private String province;

    @ApiModelProperty(value = "城市")
    @TableField("city")
    private String city;

    @ApiModelProperty(value = "区域")
    @TableField("country")
    private String country;

    @ApiModelProperty(value = "经销商ID")
    @TableField("dealer_id")
    private Integer dealerId;

    @ApiModelProperty(value = "经销商编码")
    @TableField("dealer_code")
    private String dealerCode;

    @ApiModelProperty(value = "经销商名称")
    @TableField("dealer_name")
    private String dealerName;

    @ApiModelProperty(value = "门店ID")
    @TableField("store_id")
    private String storeId;

    @ApiModelProperty(value = "门店编码")
    @TableField("store_code")
    private String storeCode;

    @ApiModelProperty(value = "门店名称")
    @TableField("store_name")
    private String storeName;

    @ApiModelProperty(value = "绑定时间")
    @TableField("binding_time")
    private Date bindingTime;

    @ApiModelProperty(value = "产生订单数")
    @TableField("order_quantity")
    private Integer orderQuantity;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    @TableField("update_time")
    private Date updateTime;

    @ApiModelProperty(value = "版本")
    @TableField("version")
    private Integer version;

    @ApiModelProperty(value = "是否删除,0为否，1为是")
    @TableField("deleted")
    @TableLogic
    private Integer deleted;


}
