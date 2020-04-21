package com.mcoding.base.doc;

import java.lang.annotation.*;

/**
 * @author wzt on 2020/4/2.
 * @version 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Step {

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
