package com.mcoding.base.core.orm;

import lombok.Data;

/**
 * 模型字段元数据
 *
 * @author wzt on 2020/2/12.
 * @version 1.0
 */
@Data
public class MetaModelField {

    /**
     * 类字段名称
     */
    private String classFieldName;

    /**
     * 表字段名称
     */
    private String tableFieldName;

    /**
     * 模型字段类型
     */
    private String modelFieldType;

    /**
     * 是否关键字查询
     */
    private boolean isKeyWorldSearch;

    /**
     * 字段是否like 查询
     */
    private boolean isLikeSearch;

    /**
     * 字段是否做升序排序
     */
    private boolean isOrderByAsc;

    /**
     * 字段是否做降序排序
     */
    private boolean isOrderByDesc;
}
