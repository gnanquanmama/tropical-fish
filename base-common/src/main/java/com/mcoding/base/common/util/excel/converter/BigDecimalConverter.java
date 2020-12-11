package com.mcoding.base.common.util.excel.converter;

import cn.hutool.core.util.StrUtil;
import jxl.Cell;
import jxl.Sheet;

import java.math.BigDecimal;
import java.util.List;

/**
 * BigDecimal转换器,如果内容为空则默认为0
 * @author zhengzhongfeng
 *
 */
public class BigDecimalConverter implements StrToObjConverter<BigDecimal>, ObjToStrConverter<BigDecimal> {

    @Override
    public BigDecimal convert(String content, List<Cell> rows, Sheet sheet) {
        content = content.trim().replaceAll("\\s+", "");
        //如果为空,就默认为0
        if(StrUtil.isEmpty(content)){
            content = "0";
        }
        BigDecimal num = null;
        try {
            num = new BigDecimal(content);
        } catch (Exception e) {
            throw new IllegalArgumentException("'"+content+"'" +"无法转为数字!");
        }
        return num;
    }

    @Override
    public String convert(BigDecimal t, Object item, int index) {
        return t.toString();
    }

}
