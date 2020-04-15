package com.mcoding.base.doc.filter;

import com.mcoding.base.doc.EventNodeStack;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import java.io.IOException;

/**
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