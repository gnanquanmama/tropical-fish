package com.mcoding.base.core.doc.filter;

import com.mcoding.base.core.doc.EventNodeStack;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import java.io.IOException;

/**
 * 方法调用树过滤器
 *
 * 用于清空 ThreadLocal 中赋值的信息
 *
 * @author wzt on 2019/11/15.
 * @version 1.0
 */
@Slf4j
public class MethodInvokeTreeFiler implements Filter {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            EventNodeStack.clear();
        }
    }
}