package com.mcoding.common.util.excel;

import jxl.Cell;
import jxl.Sheet;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author Administrator
 */
public class YesOrNoIntegerConverter implements ObjToStrConverter<Integer>, StrToObjConverter<Integer> {

    private static final String YES = "是";
    private static final String NO = "否";

    @Override
    public String convert(Integer t, Object item, int index) {
        if (t== null || !t.equals(1)) {
            return NO;
        }
        return YES;
    }

    @Override
    public Integer convert(String content, List<Cell> rows, Sheet sheet) throws Exception {
        if (StringUtils.isBlank(content) || !content.trim().equals(YES)) {
            return 0;
        }

        return 1;
    }
}
