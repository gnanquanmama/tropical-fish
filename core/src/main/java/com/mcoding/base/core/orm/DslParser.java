package com.mcoding.base.core.orm;


import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mcoding.base.common.exception.SysException;
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
public class DslParser<T> {

    private boolean isContainOrderByCommand = false;

    private int current = 1;

    private int size = 10;

    private QueryWrapper<T> queryWrapper = new QueryWrapper<>();

    private Map<String, MetaModelField> modelFieldToTableField = null;

    public static <T> DslParser<T> newWrapper() {
        return new DslParser<>();
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
    public QueryWrapper<T> parseToWrapper(JSONObject queryObject, Class<T> clazz) {
        if (Objects.isNull(queryObject) || CollectionUtil.isEmpty(queryObject.keySet())) {
            return queryWrapper;
        }

        // 获取模型字段元信息
        modelFieldToTableField = MetaModelUtils.generateMetaModelField(clazz);

        // 解析查询条件
        this.parseQueryCondition(queryObject);

        // 解析排序条件
        this.parseOrderByCondition(queryObject);

        // 解析分页信息
        this.parsePage(queryObject);

        return this.queryWrapper;
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
                    .map(MetaModelField::getTableFieldName)
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
     * @return
     */
    private void parseQueryCondition(JSONObject queryObject) {

        // between 条件做特殊处理
        Set<String> betweenKeySet = queryObject.keySet().stream()
                .filter(key -> key.contains("_$_between"))
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

            String[] fieldNameAndOperation = key.split("_\\$_");

            String modelFieldName = fieldNameAndOperation[0];
            String operation = fieldNameAndOperation[1];

            MetaModelField metaModelField = modelFieldToTableField.get(modelFieldName);
            String tableFieldName = metaModelField.getTableFieldName();

            Reflect.on(this.queryWrapper).call(operation, tableFieldName, betweenCondition.get(0), betweenCondition.get(1));
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

            Reflect.on(this.queryWrapper).call(operation, tableFieldName, value);
        }
    }

}
