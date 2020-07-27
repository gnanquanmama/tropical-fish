package com.mcoding.base.common.util;

import com.mcoding.base.common.exception.CommonException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.List;


/**
 * 帮助验证参数的断言工具
 *
 * @author hzy
 */
public class Assert {

    /**
     * 列表不能为空，否则就报错
     *
     * @param list
     * @param defaultMsg
     */
    @SuppressWarnings("rawtypes")
    public static void isNotEmpty(Collection list, String defaultMsg) {
        if (CollectionUtils.isEmpty(list))
            throw new CommonException(defaultMsg);
    }

    /**
     * 列表不能为空，否则就报错
     *
     * @param list
     * @param defaultMsg
     * @param i18n
     */
    @SuppressWarnings("rawtypes")
    public static void isNotEmpty(List list, String defaultMsg, String i18n) {
        if (CollectionUtils.isEmpty(list))
            throw new CommonException(defaultMsg, i18n);
    }

    /**
     * 值不能为空，如果是空则报错
     *
     * @param value
     * @param defaultMsg
     */
    public static void isNotBlank(String value, String defaultMsg) {
        if (StringUtils.isBlank(value))
            throw new CommonException(defaultMsg);
    }

    /**
     * 值不能为空，如果是空则报错
     *
     * @param value
     * @param defaultMsg
     * @param i18n
     */
    public static void isNotBlank(String value, String defaultMsg, String i18n) {
        if (StringUtils.isBlank(value))
            throw new CommonException(defaultMsg, i18n);
    }

    /**
     * 参数不能为空，为空报错
     *
     * @param type
     * @param mss
     */
    public static void isNotNull(Object type, String mss) {
        if (type == null)
            throw new CommonException(mss);
    }

    /**
     * 参数不能为空，为空报错
     *
     * @param type
     */
    public static void isNotNull(Object type) {
        if (type == null)
            throw new CommonException(type + "不能为空");
    }

    /**
     * 值应该存在。如果不存在，则报错
     *
     * @param list
     * @param value
     */
    public static <T> void isExists(List<T> list, T value, String msg) {
        if (!list.contains(value))
            throw new CommonException(StringUtils.defaultIfBlank(msg, "该值不存在"));

    }

    /**
     * 值应该不存在存在。如果存在，则报错
     *
     * @param obj
     * @param str
     */
    public static <T> void doNotExists(List<T> list, T value, String msg) {
        if (list.contains(value))
            throw new CommonException(StringUtils.defaultIfBlank(msg, "该值已经存在"));

    }

}
