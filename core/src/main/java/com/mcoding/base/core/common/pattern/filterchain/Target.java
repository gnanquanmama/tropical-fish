package com.mcoding.base.core.common.pattern.filterchain;

/**
 * @author wzt on 2020/5/3.
 * @version 1.0
 */
@FunctionalInterface
public interface Target<Request, Response> {

    /**
     * 执行目标方法
     *
     * @param request
     * @return
     */
    Response execute(Request request);

}
