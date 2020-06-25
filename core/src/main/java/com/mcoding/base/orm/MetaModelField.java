package com.mcoding.base.orm;

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

    private String tableFieldName;
    private String modelFieldType;
}
