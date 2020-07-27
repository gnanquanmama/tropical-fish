package com.mcoding.base.core.orm;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 模型字段元数据
 *
 * @author wzt on 2020/2/12.
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class MetaModelField {

    /**
     * 表字段名称
     */
    private String tableFieldName;

    /**
     * 模型字段类型
     */
    private String modelFieldType;
}
