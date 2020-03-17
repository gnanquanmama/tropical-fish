package com.mcoding.common.util;


import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.joor.Reflect;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author wzt on 2020/2/11.
 * @version 1.0
 */
@Getter
public class SmartWrapper<T> {

    private boolean isContainOrderByCommand = false;

    private int current = 1;

    private int size = 10;

    private QueryWrapper<T> queryWrapper = new QueryWrapper<>();

    private Map<String, ModelFieldAttr> modelFieldToTableField = null;

    public static <T> SmartWrapper<T> newWrapper() {
        return new SmartWrapper<>();
    }

    public IPage<T> generatePage() {
        return new Page<>(this.current, this.size);
    }

    /**
     * 解析JSON查询字符串, 构建QueryWrapper对象
     *
     * @param queryObject
     * @param clazz
     * @return
     */
    public void parse(JSONObject queryObject, Class<T> clazz) {
        if (Objects.isNull(queryObject)) {
            return;
        }

        // 获取模型字段元信息
        modelFieldToTableField = ModelAttrUtils.generateModelAttr(clazz);

        // 解析查询条件
        this.parseQueryCondition(queryObject, clazz);

        // 解析排序条件
        this.parseOrderByCondition(queryObject);

        // 解析分页信息
        this.parsePage(queryObject);
    }


    private void parsePage(JSONObject queryObject) {
        if (Objects.isNull(queryObject)) {
            return;
        }

        Object current = queryObject.get("current");
        Object size = queryObject.get("size");
        if (current == null || size == null) {
            return;
        }

        this.current = (int) current;
        this.size = (int) size;
    }

    /**
     * 解析排序条件
     *
     * @param queryObject
     * @return
     */
    private void parseOrderByCondition(JSONObject queryObject) {

        Set<String> defineOrderByCommandSet = Sets.newHashSet();
        defineOrderByCommandSet.add("orderByDesc");
        defineOrderByCommandSet.add("orderByAsc");

        // 过滤有排序条件的集合
        Set<Map.Entry<String, Object>> orderByEntrySet = queryObject.entrySet()
                .stream()
                .filter(entry -> {
                    String key = entry.getKey();
                    boolean isContainsKey = defineOrderByCommandSet.contains(key);
                    Object columns = queryObject.get(key);

                    return isContainsKey && columns != null && StringUtils.isNotBlank(columns.toString());
                })
                .collect(Collectors.toSet());

        if (CollectionUtil.isEmpty(orderByEntrySet)) {
            return;
        }

        this.isContainOrderByCommand = true;

        for (Map.Entry<String, Object> entry : orderByEntrySet) {
            String orderByCommand = entry.getKey();
            String orderByFields = queryObject.getString(orderByCommand);
            String[] orderByModelFieldArray = orderByFields.split(",");

            List<String> orderByTableFieldNameList = Lists.newArrayList(orderByModelFieldArray)
                    .stream()
                    .map(orderByModelField -> modelFieldToTableField.get(orderByModelField))
                    .map(ModelFieldAttr::getTableFieldName)
                    .collect(Collectors.toList());

            for (String orderByTableFieldName : orderByTableFieldNameList) {
                Reflect.on(queryWrapper).call(orderByCommand, orderByTableFieldName);
            }
        }
    }

    /**
     * 根据自定义语法解析查询条件
     *
     * @param queryObject
     * @param clazz
     * @return
     */
    private void parseQueryCondition(JSONObject queryObject, Class<T> clazz) {
        // 有效的查询条件
        Set<Map.Entry<String, Object>> validQueryObj = queryObject.entrySet()
                .stream()
                .filter(entry -> {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    // 过滤包含分隔符 _$_ 的查询key
                    boolean isValidKey = key.contains("_$_");
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
            String[] fieldNameAndOperation = key.split("_\\$_");

            String modelFieldName = fieldNameAndOperation[0];
            String operation = fieldNameAndOperation[1];

            ModelFieldAttr modelFieldAttr = modelFieldToTableField.get(modelFieldName);
            String tableFieldName = modelFieldAttr.getTableFieldName();
            String modelFieldType = modelFieldAttr.getModelFieldType();

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

            Reflect.on(this.queryWrapper).call(operation, tableFieldName, value);
        }
    }

}
