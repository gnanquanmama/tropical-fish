package com.mcoding.base.core.common.util.excel;

import jxl.Cell;
import jxl.Sheet;

import java.util.List;

/**
 * 转换器
 * @author hzy
 *
 * @param <T>
 */
public interface StrToObjConverter<T> {

    /**
     * 把excel的内容转换到指定内容
     * @param content
     * @param sheet
     * @param rows
     * @return
     * @throws Exception
     */
    public T convert(String content, List<Cell> rows, Sheet sheet) throws Exception;
}
