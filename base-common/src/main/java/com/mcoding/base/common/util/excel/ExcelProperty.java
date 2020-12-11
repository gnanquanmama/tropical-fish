package com.mcoding.base.common.util.excel;

import com.mcoding.base.common.util.excel.converter.ObjToStrConverter;

import java.lang.annotation.*;

/**
 * @author wzt on 2020/2/12.
 * @version 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ExcelProperty {

    String title();
    Class<? extends ObjToStrConverter> objToStrConverter() default ObjToStrConverter.class;
}
