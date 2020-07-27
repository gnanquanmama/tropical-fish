package com.mcoding.base.core.cache;

import com.mcoding.base.core.common.util.bean.ReflectUtils;
import com.mcoding.base.core.common.exception.CommonException;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.redisson.api.RBucket;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * @author wzt on 2020/3/11.
 * @version 1.0
 */
@Order(10)
@Aspect
@Component
public class RCacheAspect {

    @Resource
    private RedissonClient redissonClient;

    @Pointcut(value = "@annotation(com.mcoding.base.core.cache.RCacheable)")
    public void cacheablePointCut() {
    }

    @Around(value = "cacheablePointCut()")
    public Object cacheableDoAround(ProceedingJoinPoint point) throws Throwable {
        Object[] args = point.getArgs();

        Method currentMethod = ReflectUtils.getCurrentMethod(point);
        RCacheable rCacheable = currentMethod.getAnnotation(RCacheable.class);
        String key = rCacheable.key();

        String secKeySpel = rCacheable.secKey();
        if (StringUtils.isNotBlank(secKeySpel)) {
            String secKeyValue = this.parseSpel(currentMethod, args, secKeySpel, String.class, "");
            key = key + "::" + secKeyValue;
        }

        RBucket<Object> rBucket = redissonClient.getBucket(key);
        Object value = rBucket.get();
        if (value != null) {
            boolean resetTTL = rCacheable.resetTTL();
            if (resetTTL) {
                // 重置生存时间
                rBucket.expireAsync(rCacheable.ttl(), rCacheable.timeUnit());
            }
            return value;
        }

        boolean needSerial = rCacheable.serial();
        if (!needSerial) {
            // 不需要串行执行
            Object proceedResult = point.proceed(args);
            rBucket.set(proceedResult, rCacheable.ttl(), rCacheable.timeUnit());
            return proceedResult;
        }

        // 分布式锁 + double check
        RLock rLock = this.redissonClient.getLock(key + "::lock");
        try {
            long timeout = rCacheable.serialTimeOut();
            rLock.tryLock(timeout, timeout, rCacheable.timeUnit());

            Object cacheValue = redissonClient.getBucket(key).get();
            if (cacheValue != null) {
                return cacheValue;
            }

            // 执行业务逻辑并且把接口返回结果缓存
            Object proceedResult = point.proceed(args);
            rBucket.set(proceedResult, rCacheable.ttl(), rCacheable.timeUnit());

            return proceedResult;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rLock.unlock();
        }

        throw new CommonException("串行执行接口缓存异常");
    }


    @Pointcut(value = "@annotation(com.mcoding.base.core.cache.RCacheEvict)")
    public void cacheEvictPointCut() {
    }

    @Before(value = "cacheEvictPointCut()")
    public void cacheEvitDoBefore(JoinPoint joinPoint) {
        RCacheEvict rCacheEvict = ReflectUtils.getCurrentMethod(joinPoint).getAnnotation(RCacheEvict.class);
        String cacheName = rCacheEvict.key();

        RBucket<Object> rBucket = redissonClient.getBucket(cacheName);
        rBucket.delete();
    }

    private ExpressionParser parser = new SpelExpressionParser();
    private LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();

    /**
     * 解析 spel 表达式
     *
     * @param method        方法
     * @param arguments     参数
     * @param spel          表达式
     * @param clazz         返回结果的类型
     * @param defaultResult 默认结果
     * @return 执行spel表达式后的结果
     */
    private <T> T parseSpel(Method method, Object[] arguments, String spel, Class<T> clazz, T defaultResult) {
        String[] params = discoverer.getParameterNames(method);
        EvaluationContext context = new StandardEvaluationContext();
        for (int len = 0; len < params.length; len++) {
            context.setVariable(params[len], arguments[len]);
        }
        try {
            Expression expression = parser.parseExpression(spel);
            return expression.getValue(context, clazz);
        } catch (Exception e) {
            return defaultResult;
        }
    }

}
