package com.mcoding.base.core.orm;


import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 解析 where 条件处理器
 *
 * @author wzt on 2022/04/10.
 * @version 1.0
 */
@AllArgsConstructor
public class ParseSearchCondHandler implements ParseHandler {

    private JSONObject queryObject;

    private Map<String, MetaModelField> modelFieldToTableField;

    @Override
    public void apply(ParserContext parserContext) {

        List<MetaModelField> keywordFields = modelFieldToTableField.values()
                .stream()
                .filter(MetaModelField::isKeyWorldSearch)
                .collect(Collectors.toList());

        String searchKeyword = queryObject.getString("searchKeyword");

        parserContext.setSearchKeyword(searchKeyword);
        parserContext.setKeywordFieldList(keywordFields);
    }
}
