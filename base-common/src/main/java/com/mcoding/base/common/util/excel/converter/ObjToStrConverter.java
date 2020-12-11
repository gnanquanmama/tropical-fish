package com.mcoding.base.common.util.excel.converter;

public interface ObjToStrConverter<T> {

    public String convert(T t, Object item, int index);
}
