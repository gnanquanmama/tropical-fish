package com.mcoding.base.common.util.excel.converter;

import cn.hutool.core.util.StrUtil;
import jxl.Cell;
import jxl.Sheet;

import java.util.List;
import java.util.regex.Pattern;

/**
 * LongConverter
 *
 * @date 2018年2月6日 下午2:00:09
 * @version 1.0
 */
public class LongConverter implements StrToObjConverter<Long>, ObjToStrConverter<Long> {

    private static Pattern pattern = Pattern.compile("^\\-*\\d+");

    @Override
    public String convert(Long t, Object item, int index) {
        return t.toString();
    }

    @Override
    public Long convert(String content, List<Cell> rows, Sheet sheet) throws Exception {
        if (StrUtil.isBlank(content)) {
            return null;
        }

        content = content.trim();
        if (!pattern.matcher(content).matches()) {
            throw new IllegalArgumentException("数据不是整数");
        }

        return Long.valueOf(content);
    }

}
