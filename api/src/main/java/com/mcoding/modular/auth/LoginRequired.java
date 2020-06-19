package com.mcoding.modular.auth;

import java.lang.annotation.*;

/**
 * @author wzt on 2020/6/13.
 * @version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface LoginRequired {

    String sessionKey() default "currentUser";

}
