package com.mcoding.base.cache;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author wzt on 2020/3/26.
 * @version 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface RCacheable {
    /**
     * 一级缓存名称
     *
     * @return
     */
    String key();

    /**
     * 二级key,使用spel语法
     *
     * @return
     */
    String secKey() default "";

    /**
     * 缓存生存时间, 默认3000毫秒
     *
     * @return
     */
    long ttl() default 3000;

    /**
     * 缓存时间单位，默认为毫秒
     *
     * @return
     */
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;

    /**
     * 是否延长缓存时间，默认为是
     *
     * @return
     */
    boolean resetTTL() default true;

    /**
     * 是否串行执行方法
     *
     * @return
     */
    boolean serial() default false;

    /**
     * 串行执行超时时间，默认5000毫秒
     *
     * @return
     */
    long serialTimeOut() default 5000;

}
