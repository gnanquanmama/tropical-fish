package com.mcoding.base.core.cache;

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
	 * 一级缓存名称
	 * @return
	 */
	String key();

	/**
	 * 二级key,使用spel语法
	 *
	 * @return
	 */
	String secKey() default "";

}
