package com.mcoding.applet.auth.base;

import com.mcoding.applet.auth.util.LoginUserUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author wzt on 2020/6/13.
 * @version 1.0
 */
@Slf4j
public class LoginRequiredArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        //匹配参数上具有@LoginRequired注解的参数
        return methodParameter.hasParameterAnnotation(LoginRequired.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) {

        return LoginUserUtils.getRegisterBo();
    }
}