package com.mcoding.base.core.orm;

import java.lang.annotation.*;

/**
 * 搜索关键字注解
 *
 * Created on 2022/4/10.
 *
 * @author wzt
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Keyword {
}
