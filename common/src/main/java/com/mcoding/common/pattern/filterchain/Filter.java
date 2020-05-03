package com.mcoding.common.pattern.filterchain;

/**
 * @author wzt on 2020/5/3.
 * @version 1.0
 */
public interface Filter<Request> {


    /**
     * 过滤
     *
     * @param request
     * @param filterContext
     */
    void doFilter(Request request, FilterContext<Request, ?> filterContext);

}
