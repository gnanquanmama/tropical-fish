package com.mcoding.base.log;

import java.lang.annotation.*;

/**
 * @author wzt on 2020/3/11.
 * @version 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface MdcLog {
}
