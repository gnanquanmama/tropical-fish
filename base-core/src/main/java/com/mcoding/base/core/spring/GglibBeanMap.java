package com.mcoding.base.core.spring;

import com.google.common.collect.Maps;
import com.mcoding.base.common.exception.SysException;
import org.springframework.cglib.beans.BeanMap;

import java.util.Collections;
import java.util.Map;

/**
 * @author wzt on 2020/6/24.
 * @version 1.0
 */
public class GglibBeanMap {

    /**
     * 将对象装换为map
     *
     * @param bean
     * @return
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        if (bean == null) {
            return Collections.emptyMap();
        }

        Map<String, Object> map = Maps.newHashMap();
        BeanMap beanMap = BeanMap.create(bean);
        for (Object key : beanMap.keySet()) {
            map.put(key + "", beanMap.get(key));
        }

        return map;
    }

    /**
     * 将map装换为javabean对象
     *
     * @param map
     * @param beanClass
     * @return
     */
    public static <T> T mapToBean(Map<String, Object> map, Class<T> beanClass) {
        T bean = null;
        try {
            bean = beanClass.newInstance();
        } catch (Exception e) {
            throw new SysException(e.getMessage());
        }

        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
        return bean;
    }

}
