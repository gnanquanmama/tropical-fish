package com.mcoding.base.core.orm;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author wzt on 2020/7/7.
 * @version 1.0
 */
@Data
class ParserContext {

    /**
     * 存储 where 条件表
     */
    private List<WhereCondition> whereConditionList = Lists.newArrayList();

    /**
     * 存储 orderBy 条件
     */
    private Map<String, String> orderByMap = Maps.newLinkedHashMap();

    /**
     * 关键字字段
     */
    private List<MetaModelField> keywordFieldList;

    /**
     * 搜索关键词
     */
    private String searchKeyword;

    private int current = 1;

    private int size = 10;

    void addQueryCondition(String operation, String tableFieldName, Object value) {
        whereConditionList.add(new WhereCondition(operation, tableFieldName, value));
    }

    void addOrderByCondition(String orderByCommand, String orderByTableFieldName) {
        this.orderByMap.put(orderByCommand, orderByTableFieldName);
    }

}
