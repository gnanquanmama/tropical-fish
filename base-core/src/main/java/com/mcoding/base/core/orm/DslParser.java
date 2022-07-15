package com.mcoding.base.core.orm;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.core.toolkit.support.SerializedLambda;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.mcoding.base.common.exception.CommonException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.joor.Reflect;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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

    private JSONObject queryObject;

    private QueryWrapper<T> queryWrapper = new QueryWrapper<>();

    public DslParser(JSONObject queryObject) {
        this.queryObject = queryObject;
    }

    /**
     * 解析JSON查询字符串, 构建QueryWrapper对象
     *
     * @param clazz
     * @return
     */
    public QueryWrapper<T> parseToWrapper(Class<T> clazz) {
        if (Objects.isNull(queryObject) || CollectionUtil.isEmpty(queryObject.keySet())) {
            return queryWrapper;
        }

        // 获取模型字段元信息
        Map<String, MetaModelField> modelFieldToTableField = MetaModelUtils.generateMetaModelField(clazz);

        List<ParseHandler> parseHandlerList = Lists.newArrayList();
        parseHandlerList.add(new ParseWhereCondHandler(queryObject, modelFieldToTableField));
        parseHandlerList.add(new ParseOrderByCondHandler(queryObject, modelFieldToTableField));
        parseHandlerList.add(new ParsePageHandler(queryObject));
        parseHandlerList.add(new ParseSearchCondHandler(queryObject, modelFieldToTableField));

        ParserContext parserContext = new ParserContext();
        for (ParseHandler parseHandler : parseHandlerList) {
            parseHandler.apply(parserContext);
        }

        log.info("EVENT=解析请求参数|RESULT={}", JSONObject.toJSONString(parserContext));

        List<WhereCondition> whereCondList = parserContext.getWhereConditionList();
        this.executeWhereCondOpr(whereCondList);

        Map<String, String> orderByMap = parserContext.getOrderByMap();
        this.executeOrderByOpr(orderByMap);

        List<MetaModelField> keywordFieldList = parserContext.getKeywordFieldList();
        this.executeKeyWordSearch(keywordFieldList, parserContext.getSearchKeyword());

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
                Date startTime = new Date((Long) valueList.get(0));
                Date endTime = new Date((Long) valueList.get(1));

                Reflect.on(this.queryWrapper).call(operation, tableFileName, startTime, endTime);
            } else if ("isNull".equalsIgnoreCase(operation) || "isNotNull".equalsIgnoreCase(operation)) {
                Reflect.on(this.queryWrapper).call(operation, tableFileName);
            } else {
                Reflect.on(this.queryWrapper).call(operation, tableFileName, value);
            }
        });
    }

    private void executeOrderByOpr(Map<String, String> orderByMap) {
        if (CollectionUtil.isEmpty(orderByMap)) {
            return;
        }

        orderByMap.forEach(
                (orderByCmd, tableFileName) ->
                        Reflect.on(this.queryWrapper).call(orderByCmd, tableFileName));
    }

    private void executeKeyWordSearch(List<MetaModelField> keywordFieldList, String keyword) {
        if (CollectionUtil.isEmpty(keywordFieldList)) {
            return;
        }

        String fieldNameJoinStr = keywordFieldList.stream()
                .map(MetaModelField::getTableFieldName)
                .collect(Collectors.joining(","));

        this.queryWrapper.like("concat( " + fieldNameJoinStr + ") ", keyword);
    }

    public IPage<T> generatePage() {
        return new Page<>(this.current, this.size);
    }

    /**
     * 首字母转小写
     *
     * @param str
     * @return
     */
    private String toLowerCaseFirstOne(String str) {
        if (Character.isLowerCase(str.charAt(0)))
            return str;
        else
            return (new StringBuilder())
                    .append(Character.toLowerCase(str.charAt(0)))
                    .append(str.substring(1))
                    .toString();
    }

    /**
     * 获取查询条件的值
     *
     * @param column
     * @param oprEnum
     * @return
     */
    public <R> R getPropValue(SFunction<T, ?> column, OprEnum oprEnum, Class<R> clazz) {

        SerializedLambda lambda = SerializedLambda.resolve(column);
        String methodName = lambda.getImplMethodName();

        String prefix = null;
        if (methodName.startsWith("get")) {
            prefix = "get";
        }
        if (methodName.startsWith("is")) {
            prefix = "is";
        }
        if (prefix == null) {
            throw new CommonException("无效的getter方法: " + methodName);
        }

        String fieldName = this.toLowerCaseFirstOne(methodName.replace(prefix, ""));
        return (R) queryObject.get(fieldName + "_$_" + oprEnum.getValue());
    }

}
