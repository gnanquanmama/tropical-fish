package com.mcoding.base.cache;

import java.lang.annotation.*;

/**
 * @author wzt on 2020/3/26.
 * @version 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface RCacheEvict {
    /**
     * 缓存名称
     * @return
     */
    String key();

}
