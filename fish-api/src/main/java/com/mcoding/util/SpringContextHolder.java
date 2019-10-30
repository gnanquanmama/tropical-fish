package com.mcoding.util;

/**
 * @author wzt on 2019/10/30.
 * @version 1.0
 */

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author wzt on 2019/3/19.
 * @version 1.0
 */
@Component
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        applicationContext = arg0;
    }

    /**
     * 取得存储在静态变量中的ApplicationContext.
     */
    public static ApplicationContext getApplicationContext() {
        checkApplicationContext();
        return applicationContext;
    }

    /**
     * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        return (T) getApplicationContext().getBean(name);
    }

    /**
     * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    public static <T> T getOneBean(Class<T> clazz) {
        Map<String, T> beanMaps = getApplicationContext().getBeansOfType(clazz);
        if (beanMaps!=null && !beanMaps.isEmpty()) {
            return beanMaps.values().iterator().next();
        } else{
            return null;
        }
    }

    public static <T> Map<String, T> getBeans(Class<T> clazz) {
        Map<String, T> beanMaps = getApplicationContext().getBeansOfType(clazz);
        return beanMaps;
    }

    private static void checkApplicationContext() {
        if (applicationContext == null) {
            throw new IllegalStateException("spring 的配置文件中，未配置SpringContextHolder");
        }
    }

    /**
     * 获取系统的配置
     * @param key
     * @return
     */
    public static String getProperty(String key){
        return getApplicationContext().getEnvironment().getProperty(key);
    }

    /**
     * 获取系统的配置
     * @param key
     * @return
     */
    public static String getProperty(String key, String defaultValue){
        return getApplicationContext().getEnvironment().getProperty(key, defaultValue);
    }

}