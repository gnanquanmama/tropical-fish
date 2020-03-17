package com.mcoding.common.util.excel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class ConverterFactory {

    private final Map<String, StrToObjConverter> toObjMap = new HashMap<String, StrToObjConverter>();

    private final Map<String, ObjToStrConverter> toStrMap = new HashMap<String, ObjToStrConverter>();

    private static ConverterFactory factory = new ConverterFactory();

    /**
     * 注册默认的转换器
     */
    private void registe() {
        toObjMap.put(Integer.class.toString(), new IntegerConverter());
        toObjMap.put(BigDecimal.class.toString(), new BigDecimalConverter());
        toObjMap.put(Date.class.toString(), new DateConverter());
        toObjMap.put(Long.class.toString(), new LongConverter());

        toStrMap.put(Integer.class.toString(), new IntegerConverter());
        toStrMap.put(BigDecimal.class.toString(), new BigDecimalConverter());
        toStrMap.put(Date.class.toString(), new DateConverter());
        toStrMap.put(Long.class.toString(), new LongConverter());
    }

    public static StrToObjConverter getDefaultToObjConverter(Class clazz) {
        return factory.getToObjMap().get(clazz.toString());
//        if (clazz.equals(Integer.class) || clazz.equals(BigDecimal.class) || clazz.equals(Date.class)
//                || clazz.equals(Long.class)) {
//        }
//
//        return null;
    }

    public static ObjToStrConverter getDefaultToStrConverter(Class clazz) {
        return factory.getToStrMap().get(clazz.toString());
//        if (clazz.equals(Integer.class) || clazz.equals(BigDecimal.class) || clazz.equals(Date.class)
//                || clazz.equals(Long.class)) {
//        }
//
//        return null;
    }

    private ConverterFactory() {
        super();
        this.registe();
    }

    public Map<String, StrToObjConverter> getToObjMap() {
        return toObjMap;
    }

    public Map<String, ObjToStrConverter> getToStrMap() {
        return toStrMap;
    }

}
