package com.mcoding.base.core.orm;

import java.lang.annotation.*;

/**
 * 倒序
 *
 * @author wzt
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface OrderByDesc {
}
