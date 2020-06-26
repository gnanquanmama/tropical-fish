package com.mcoding.base.common.util.excel;

import jxl.Cell;
import jxl.Sheet;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;
import java.util.List;

public class DateConverter implements StrToObjConverter<Date>, ObjToStrConverter<Date> {

    //	private static final SimpleDateFormat DEFAULT_DATE_FORMATE = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String DEFAULT_DATE_FORMATE = "yyyy-MM-dd HH:mm:ss";
//
//	private SimpleDateFormat simpleDateFormat;

    private String dateFormat;

    public String getDateFormat() {
        return dateFormat;
    }

    public DateConverter() {
        super();
    }

    public DateConverter(String dateFormat) {
        super();
        this.dateFormat = dateFormat;
    }

    public DateConverter setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
        return this;
    }

    @Override
    public Date convert(String content, List<Cell> rows, Sheet sheet) throws Exception {
        if (StringUtils.isBlank(content)) {
            return null;
        }
        if (StringUtils.isNotBlank(dateFormat)) {
            return DateUtils.parseDate(content, new String[]{ dateFormat});
        }

        if (content.matches("\\d+")) {
            return new Date(Long.valueOf(content));
        }
        if (content.matches("\\d+-\\d+-\\d+\\s\\d+:\\d+:\\d+") || content.matches("\\d+-\\d+-\\d+")) {
            return DateUtils.parseDate(content, new String[]{ "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd"});
        }else{
            throw new RuntimeException("无法识别该日期格式");
        }
    }

    @Override
    public String convert(Date t, Object item, int index) {
        if (StringUtils.isBlank(dateFormat)) {
            dateFormat = DEFAULT_DATE_FORMATE;
        }

        return DateFormatUtils.format(t, this.dateFormat);
    }

}
