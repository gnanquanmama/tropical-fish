package com.mcoding.base.core.doc;

import com.mcoding.base.common.exception.SysException;
import com.mcoding.base.common.util.constant.MdcConstants;
import com.mcoding.base.core.utils.ReflectUtils;
import javassist.*;
import javassist.bytecode.MethodInfo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 事件追踪切面
 * <p>
 * warning : 切面有大的性能消耗，禁止在生产环境中打开
 * <p>
 * <p>
 * {@link Process 过程}
 * {@link Phase 阶段}
 * {@link Step 步骤}
 * <p>
 * 复杂的业务的一个过程，可以拆分为多个阶段，每个阶段可以有多个步骤。
 * 在业务的代码链路上，加上注解 @Process, @Phase, @Step , 就可以在运行态下，可以得到一颗方法调用树
 *
 * @author wzt on 2020/4/2.
 * @version 1.0
 */
@Profile({"dev", "test"})
@Order(1)
@Aspect
@Component
public class EventTraceAspect {

    private static AtomicLong idAdder = new AtomicLong();

    @Pointcut("@annotation(com.mcoding.base.core.doc.Process) || @annotation(com.mcoding.base.core.doc.Phase) || @annotation(com.mcoding.base.core.doc.Step)")
    public void tracePointCut() {

    }

    @Before("tracePointCut()")
    public void doBefore(JoinPoint joinPoint) {

        Method method = ReflectUtils.getCurrentMethod(joinPoint);
        int lineNumber = this.getLineNumber(method);
        String methodName = method.getDeclaringClass().getSimpleName() + "." + method.getName();

        boolean isProcessPresent = method.isAnnotationPresent(Process.class);
        boolean isPhasePresent = method.isAnnotationPresent(Phase.class);
        boolean isStepPresent = method.isAnnotationPresent(Step.class);

        EventNode preOrderNode = EventNodeStack.peek();
        if (preOrderNode == null && isProcessPresent) {
            Process process = method.getAnnotation(Process.class);

            EventNode rootNode = new EventNode();
            String traceId = MDC.get(MdcConstants.TRACE_ID);
            rootNode.setTraceId(traceId);
            rootNode.setLineNum(lineNumber);
            rootNode.setId(0);
            rootNode.setParentId(-1);
            rootNode.setMethod(methodName);
            rootNode.setEvent(process.comment());
            rootNode.setSync(process.sync());
            rootNode.setLifeCycle("process");

            EventNodeContainer.put(traceId, rootNode);
            EventNodeStack.push(rootNode);
            return;
        }

        if (preOrderNode == null) {
            throw new SysException("请检测 @Process，@Phase，@Step 注解是否放在正确的位置");
        }

        long parentId = preOrderNode.getId();
        String traceId = preOrderNode.getTraceId();
        EventNode childNode = new EventNode();
        childNode.setTraceId(traceId);
        childNode.setId(idAdder.incrementAndGet());
        childNode.setParentId(parentId);
        childNode.setLineNum(lineNumber);
        childNode.setMethod(methodName);

        if (isPhasePresent) {
            Phase phase = method.getAnnotation(Phase.class);
            childNode.setEvent(phase.comment());
            childNode.setLifeCycle("phase");
            childNode.setSync(phase.sync());
        }

        if (isStepPresent) {
            Step step = method.getAnnotation(Step.class);
            childNode.setEvent(step.comment());
            childNode.setLifeCycle("step");
            childNode.setSync(step.sync());
        }

        EventNodeContainer.put(traceId, childNode);
        EventNodeStack.push(childNode);
    }

    @After("tracePointCut()")
    public void doAfter(JoinPoint joinPoint) {
        int size = EventNodeStack.size();
        if (size > 1) {
            EventNodeStack.pop();
        }
    }

    private int getLineNumber(Method method) {
        try {
            CtMethod ctMethod = this.getCtMethod(method);
            MethodInfo methodInfo = ctMethod.getMethodInfo2();
            return methodInfo.getLineNumber(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    private CtMethod getCtMethod(Method method) throws NotFoundException {
        String className = method.getDeclaringClass().getName();
        String methodName = method.getName();

        ClassPool classPool = ClassPool.getDefault();
        classPool.appendClassPath(new LoaderClassPath(Thread.currentThread().getContextClassLoader()));
        CtClass ctClass = classPool.get(className);

        return ctClass.getDeclaredMethod(methodName);
    }

}
