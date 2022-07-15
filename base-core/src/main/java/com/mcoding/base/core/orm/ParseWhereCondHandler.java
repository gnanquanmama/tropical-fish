package com.mcoding.base.core.orm;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.mcoding.base.common.exception.SysException;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 解析 where 条件处理器
 *
 * @author wzt on 2020/7/7.
 * @version 1.0
 */
@AllArgsConstructor
public class ParseWhereCondHandler implements ParseHandler {

    private JSONObject queryObject;
    private Map<String, MetaModelField> modelFieldToTableField;

    @Override
    public void apply(ParserContext parserContext) {

        // between 做特殊处理
        Set<String> betweenKeySet = queryObject.keySet().stream()
                .filter(key -> key.contains(".between"))
                .collect(Collectors.toSet());

        for (String key : betweenKeySet) {
            Object value = queryObject.get(key);
            if (!(value instanceof List)) {
                throw new SysException(String.format("%s 的值必须为数组", key));
            }

            List betweenCondition = (List) value;
            if (betweenCondition.size() != 2) {
                throw new SysException(String.format("%s 的值必须为两个", key));
            }

            String[] fieldNameAndOperation = key.split("\\.");

            String operation = fieldNameAndOperation[1];
            String modelFieldName = fieldNameAndOperation[0];

            MetaModelField metaModelField = modelFieldToTableField.get(modelFieldName);
            String tableFieldName = metaModelField.getTableFieldName();

            parserContext.addQueryCondition(operation, tableFieldName, value);
        }

        // 清空已设置 between 查询条件
        for (String key : betweenKeySet) {
            queryObject.remove(key);
        }

        // 有效的查询条件
        Set<Map.Entry<String, Object>> validQueryObj = queryObject.entrySet()
                .stream()
                .filter(entry -> {
                    String key = entry.getKey();
                    Object value = entry.getValue();

                    // isNull 或者 isNotNull 查询字段值为空，不需要做校验过滤
                    if (key.contains(".isNull") || key.contains(".isNotNull")) {
                        return true;
                    }

                    // 过滤包含分隔符 . 的查询key
                    boolean isValidKey = key.contains(".");
                    // 过滤查询值为非空的key
                    boolean isValidValue = (value != null && StringUtils.isNotBlank(value.toString()));
                    if (value instanceof List) {
                        // 如果查询条件是空列表，则该查询条件不生效
                        if (CollectionUtil.isEmpty((List) value)) {
                            isValidValue = false;
                        }
                    }
                    return isValidKey && isValidValue;
                }).collect(Collectors.toSet());

        for (Map.Entry<String, Object> entry : validQueryObj) {
            String key = entry.getKey();
            String[] fieldNameAndOperation = key.split("\\.");

            String modelFieldName = fieldNameAndOperation[0];
            String operation = fieldNameAndOperation[1];

            MetaModelField metaModelField = modelFieldToTableField.get(modelFieldName);
            String tableFieldName = metaModelField.getTableFieldName();
            String modelFieldType = metaModelField.getModelFieldType();

            Object value = entry.getValue();
            // 如果定义类型是java.util.Date类型，则把时间戳转换为Date对象
            if (Date.class.getTypeName().equals(modelFieldType)) {
                value = new Date((Long) value);
            }

            if ("in".equals(operation)) {
                // 如果in的操作类型是字符串，则按照逗号，拆分为数组
                if (value instanceof String) {
                    value = ((String) value).split(",");
                }
            }

            parserContext.addQueryCondition(operation, tableFieldName, value);
        }
    }
}
