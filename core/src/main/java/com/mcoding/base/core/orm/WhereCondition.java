package com.mcoding.base.core.orm;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author wzt on 2020/7/7.
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class WhereCondition {

    private String operation;
    private String tableFieldName;
    private Object value;

}
