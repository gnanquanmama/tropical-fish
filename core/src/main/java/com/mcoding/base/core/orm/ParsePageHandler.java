package com.mcoding.base.core.orm;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;

import java.util.Objects;

/**
 * 解析分页信息处理器
 *
 * @author wzt on 2020/7/7.
 * @version 1.0
 */
@AllArgsConstructor
public class ParsePageHandler implements ParseHandler {

    private JSONObject queryObject;

    @Override
    public void apply(ParserContext parserContext) {

        if (Objects.isNull(queryObject)) {
            return;
        }

        Object current = queryObject.get("current");
        Object size = queryObject.get("size");
        if (current == null || size == null) {
            return;
        }

        parserContext.setCurrent((int) current);
        parserContext.setSize((int) size);

    }
}
