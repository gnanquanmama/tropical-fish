package com.mcoding.base.core.doc;

import java.lang.annotation.*;

/**
 * 步骤注解
 *
 * @author wzt on 2020/4/2.
 * @version 1.0
 * {@link EventTraceAspect}
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
