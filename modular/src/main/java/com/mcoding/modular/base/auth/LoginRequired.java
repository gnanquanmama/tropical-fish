package com.mcoding.modular.base.auth;

import java.lang.annotation.*;

/**
 * @author wzt on 2020/6/13.
 * @version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface LoginRequired {

}
