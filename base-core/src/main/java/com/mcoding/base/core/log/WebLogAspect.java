package com.mcoding.base.core.log;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author wzt on 2020/7/31.
 * @version 1.0
 */
@Slf4j
@Aspect
@Component
public class WebLogAspect {

    /**
     * Swagger @ApiOperation注解为切点
     */
    @Pointcut("@annotation(io.swagger.annotations.ApiOperation)")
    public void webApiOperation() {
    }

    @Before("webApiOperation()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String url = request.getRequestURI();
        String methodDesc = getAspectLogDescription(joinPoint);
        String className = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        String ip = request.getRemoteAddr();

        String contentType = request.getContentType();
        String requestArgs = "";
        if (contentType != null && contentType.contains("multipart/form-data")) {
            requestArgs = "content type 为 multipart/form-data, 不解析参数";
        } else {
            try {
                requestArgs = JSON.toJSONString(joinPoint.getArgs());
            } catch (Exception e) {
                e.printStackTrace();
                log.error("http请求参数序列化异常{}", e.getMessage());
            }
        }

        log.info("EVENT=打印请求日志|URL={}|Class-Method={}|METHOD-DESC={}|IP = {}|REQUEST_ARGS = {}",
                url, className, methodDesc, ip, requestArgs);

    }

    @Around("webApiOperation()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        log.info("EVENT=打印请求耗时Time-Cost= {} ms", System.currentTimeMillis() - startTime);
        return result;
    }

    private String getAspectLogDescription(JoinPoint joinPoint)
            throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();

        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    return method.getAnnotation(ApiOperation.class).value();
                }
            }
        }

        return "";
    }

}
