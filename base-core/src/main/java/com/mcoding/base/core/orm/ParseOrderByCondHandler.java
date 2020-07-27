package com.mcoding.base.core.orm;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 解析排序条件处理器
 *
 * @author wzt on 2020/7/7.
 * @version 1.0
 */
@AllArgsConstructor
public class ParseOrderByCondHandler implements ParseHandler {

    private JSONObject queryObject;
    private Map<String, MetaModelField> modelFieldToTableField;

    @Override
    public void apply(ParserContext parserContext) {

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
                parserContext.addOrderByCondition(orderByCommand, orderByTableFieldName);
            }
        }

    }
}
