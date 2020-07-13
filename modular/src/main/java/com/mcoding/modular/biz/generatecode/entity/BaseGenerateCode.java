package com.mcoding.modular.biz.generatecode.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 编码生成规则
 * </p>
 *
 * @author wzt
 * @since 2020-02-09
 */
@TableName("base_generate_code")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BaseGenerateCode implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /**
     * 名称
     */
    @TableField("name")
    private String name;

    /**
     * 目标
     */
    @TableField("target_code")
    private String targetCode;

    /**
     * 生成策略:自增策略auto_increment
     */
    @TableField("strategy")
    private String strategy;

    /**
     * 前缀
     */
    @TableField("prefix")
    private String prefix;

    /**
     * 后缀
     */
    @TableField("suffix")
    private String suffix;

    /**
     * 生成的下一个号码
     */
    @TableField("current_code")
    private String currentCode;

    /**
     * 开始的号码
     */
    @TableField("start_code")
    private String startCode;

    /**
     * 最大的值
     */
    @TableField("max_code")
    private String maxCode;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;

    /**
     * 版本
     */
    @Version
    @TableField("version")
    private Integer version;

}
