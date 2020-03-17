package com.mcoding.common.util.excel;

import jxl.Cell;
import jxl.Sheet;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.regex.Pattern;

public class IntegerConverter implements StrToObjConverter<Integer>, ObjToStrConverter<Integer>{

    private static Pattern pattern = Pattern.compile("^\\-*\\d+");

    @Override
    public Integer convert(String content, List<Cell> rows, Sheet sheet) {
        if (StringUtils.isBlank(content)) {
            return null;
        }

        content = content.trim();
        if (!pattern.matcher(content).matches()) {
            throw new IllegalArgumentException("数据不是整数");
        }

        return Integer.valueOf(content);
    }

    @Override
    public String convert(Integer t, Object item, int index) {
        return t.toString();
    }

}
