package com.mcoding.base.core.orm;


import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.joor.Reflect;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 自定义查询语法 解析器
 *
 * @author wzt on 2020/2/11.
 * @version 1.0
 */
@Slf4j
@Getter
public class DslParser<T> {

    private int current = 1;

    private int size = 10;

    private QueryWrapper<T> queryWrapper = new QueryWrapper<>();

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
        Map<String, MetaModelField> modelFieldToTableField = MetaModelUtils.generateMetaModelField(clazz);

        List<ParseHandler> parseHandlerList = Lists.newArrayList();
        parseHandlerList.add(new ParseWhereCondHandler(queryObject, modelFieldToTableField));
        parseHandlerList.add(new ParseOrderByCondHandler(queryObject, modelFieldToTableField));
        parseHandlerList.add(new ParsePageHandler(queryObject));

        ParserContext parserContext = new ParserContext();
        for (ParseHandler parseHandler : parseHandlerList) {
            parseHandler.apply(parserContext);
        }

        log.info("EVENT=解析请求参数|RESULT={}", JSONObject.toJSONString(parserContext));

        List<WhereCondition> whereCondList = parserContext.getWhereConditionList();
        this.executeWhereCondOpr(whereCondList);

        Map<String, String> orderByMap = parserContext.getOrderByMap();
        this.executeOrderByOpr(orderByMap);

        this.current = parserContext.getCurrent();
        this.size = parserContext.getSize();

        return this.queryWrapper;
    }

    private void executeWhereCondOpr(List<WhereCondition> whereCondList) {
        if (CollectionUtil.isEmpty(whereCondList)) {
            return;
        }

        whereCondList.forEach(cond -> {
            String operation = cond.getOperation();
            String tableFileName = cond.getTableFieldName();
            Object value = cond.getValue();

            if ("between".equalsIgnoreCase(operation)) {
                List valueList = (List) value;
                Reflect.on(this.queryWrapper).call(operation, tableFileName, valueList.get(0), valueList.get(1));
            } else {

                Reflect.on(this.queryWrapper).call(operation, tableFileName, value);
            }
        });
    }

    private void executeOrderByOpr(Map<String, String> orderByMap) {
        if (CollectionUtil.isEmpty(orderByMap)) {
            return;
        }

        orderByMap.forEach((orderByCmd, tableFileName) -> Reflect.on(this.queryWrapper).call(orderByCmd, tableFileName));
    }
}
