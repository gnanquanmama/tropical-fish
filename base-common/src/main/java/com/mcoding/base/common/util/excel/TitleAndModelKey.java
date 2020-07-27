package com.mcoding.base.common.util.excel;

import jxl.write.WritableCellFormat;

/**
 * excel 行头 与 实体属性的关联
 *
 * @author hzy
 *
 */
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

    public TitleAndModelKey() {
        super();
    }

    public TitleAndModelKey(int columIndex, String modelKey) {
        super();
        this.columIndex = columIndex;
        this.modelKey = modelKey;
    }

    public TitleAndModelKey(String title, String modelKey) {
        super();
        this.title = title;
        this.modelKey = modelKey;
    }

    public TitleAndModelKey(String title, String modelKey, StrToObjConverter toObjConverter) {
        super();
        this.title = title;
        this.modelKey = modelKey;
        this.toObjConverter = toObjConverter;
    }

    public TitleAndModelKey(String title, String modelKey, ObjToStrConverter toStrConverter) {
        super();
        this.title = title;
        this.modelKey = modelKey;
        this.toStrConverter = toStrConverter;
    }

    public TitleAndModelKey(int columIndex, String modelKey, ObjToStrConverter toStrConverter) {
        super();
        this.columIndex = columIndex;
        this.modelKey = modelKey;
        this.toStrConverter = toStrConverter;
    }

    public TitleAndModelKey(int columIndex, String modelKey, StrToObjConverter toObjConverter) {
        super();
        this.columIndex = columIndex;
        this.modelKey = modelKey;
        this.toObjConverter = toObjConverter;
    }

    public TitleAndModelKey(Integer columIndex, String title, String modelKey, String defaultValue,
                            StrToObjConverter toObjConverter, ObjToStrConverter toStrConverter, WritableCellFormat titleFormat,
                            WritableCellFormat contentFormat) {
        super();
        this.columIndex = columIndex;
        this.title = title;
        this.modelKey = modelKey;
        this.defaultValue = defaultValue;
        this.toObjConverter = toObjConverter;
        this.toStrConverter = toStrConverter;
        this.titleFormat = titleFormat;
        this.contentFormat = contentFormat;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getModelKey() {
        return modelKey;
    }

    public void setModelKey(String modelKey) {
        this.modelKey = modelKey;
    }

    public Integer getColumIndex() {
        return columIndex;
    }

    public void setColumIndex(Integer columIndex) {
        this.columIndex = columIndex;
    }

    public StrToObjConverter getToObjConverter() {
        return toObjConverter;
    }

    public void setToObjConverter(StrToObjConverter toObjConverter) {
        this.toObjConverter = toObjConverter;
    }

    public ObjToStrConverter getToStrConverter() {
        return toStrConverter;
    }

    public void setToStrConverter(ObjToStrConverter toStrConverter) {
        this.toStrConverter = toStrConverter;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public WritableCellFormat getTitleFormat() {
        return titleFormat;
    }

    public void setTitleFormat(WritableCellFormat titleFormat) {
        this.titleFormat = titleFormat;
    }

    public WritableCellFormat getContentFormat() {
        return contentFormat;
    }

    public void setContentFormat(WritableCellFormat contentFormat) {
        this.contentFormat = contentFormat;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

}
