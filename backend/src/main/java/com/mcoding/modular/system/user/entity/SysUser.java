package com.mcoding.modular.system.user.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import com.baomidou.mybatisplus.annotation.TableName;

/**
 * <p>
 * 管理员表
 * </p>
 *
 * @author wzt
 * @since 2020-07-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_user")
@ApiModel(value="SysUser", description="管理员表")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "头像")
    @TableField("avatar")
    private String avatar;

    @ApiModelProperty(value = "账号")
    @TableField("account")
    private String account;

    @ApiModelProperty(value = "密码")
    @TableField("password")
    private String password;

    @ApiModelProperty(value = "md5密码盐")
    @TableField("salt")
    private String salt;

    @ApiModelProperty(value = "名字")
    @TableField("name")
    private String name;

    @ApiModelProperty(value = "生日")
    @TableField("birthday")
    private Date birthday;

    @ApiModelProperty(value = "性别(字典)")
    @TableField("sex")
    private String sex;

    @ApiModelProperty(value = "电子邮件")
    @TableField("email")
    private String email;

    @ApiModelProperty(value = "电话")
    @TableField("phone")
    private String phone;

    @ApiModelProperty(value = "角色id(多个逗号隔开)")
    @TableField("role_id")
    private String roleId;

    @ApiModelProperty(value = "部门id(多个逗号隔开)")
    @TableField("dept_id")
    private Long deptId;

    @ApiModelProperty(value = "状态(字典)")
    @TableField("status")
    private String status;

    @ApiModelProperty(value = "创建时间")
    @TableField("create_time")
    private Date createTime;

    @ApiModelProperty(value = "创建人")
    @TableField("create_user")
    private Long createUser;

    @ApiModelProperty(value = "更新时间")
    @TableField("update_time")
    private Date updateTime;

    @ApiModelProperty(value = "更新人")
    @TableField("update_user")
    private Long updateUser;

    @ApiModelProperty(value = "乐观锁")
    @TableField("version")
    private Integer version;


}
