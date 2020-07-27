package com.mcoding.base.core.log;

import com.mcoding.base.core.common.util.id.RandomIdGenerator;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

/**
 * @author wzt on 2020/3/11.
 * @version 1.0
 */
@Aspect
@Component
public class MdcAspect {

    private RandomIdGenerator randomIdGenerator = new RandomIdGenerator();

    @Pointcut(value = "@annotation(com.mcoding.base.core.log.MdcLog)")
    public void pointCut() {
    }


    @Before(value = "pointCut()")
    public void doBefore(JoinPoint joinPoint) {
        MDC.put("traceID", randomIdGenerator.generate());
    }


    @After(value = "pointCut()")
    public void doAfter(JoinPoint joinPoint) {
        MDC.clear();
    }
}
