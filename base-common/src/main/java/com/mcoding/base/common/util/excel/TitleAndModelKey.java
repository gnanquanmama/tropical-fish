package com.mcoding.base.common.util.excel;

import com.mcoding.base.common.util.excel.converter.ObjToStrConverter;
import com.mcoding.base.common.util.excel.converter.StrToObjConverter;
import jxl.write.WritableCellFormat;
import lombok.Data;

/**
 * excel 行头 与 实体属性的关联
 *
 * @author hzy
 *
 */
@Data
public class TitleAndModelKey {

    private Integer columIndex;

    private String title;

    private String modelKey;

    private String defaultValue = "";

    /**是否必填**/
    private boolean required = false;

    /** excel导入时的转化器，字符串转实体属性的 **/
    private StrToObjConverter toObjConverter;

    /** 导出到excel时的转化器，实体属性转字符串 **/
    private ObjToStrConverter toStrConverter;

    /** 小标题内容字体titleFont **/
    private WritableCellFormat titleFormat;

    /** 内容字体设置cellFont **/
    private WritableCellFormat contentFormat;

    public TitleAndModelKey(String title, String modelKey) {
        this.title = title;
        this.modelKey = modelKey;
    }

    public TitleAndModelKey(String title, String modelKey, ObjToStrConverter toStrConverter) {
        this.title = title;
        this.modelKey = modelKey;
        this.toStrConverter = toStrConverter;
    }
}
