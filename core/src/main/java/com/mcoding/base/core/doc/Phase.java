package com.mcoding.base.core.doc;

import java.lang.annotation.*;

/**
 * @author wzt on 2020/4/4.
 * @version 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Phase {

    /**
     * 备注
     *
     * @return
     */
    String comment();

    /**
     * 是否同步，默认为同步
     *
     * @return
     */
    boolean sync() default true;

}
