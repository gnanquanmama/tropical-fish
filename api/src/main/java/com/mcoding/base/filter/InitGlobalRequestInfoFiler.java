package com.mcoding.base.filter;

import com.mcoding.common.id.IdGenerator;
import com.mcoding.common.id.RandomIdGenerator;
import org.slf4j.MDC;

import javax.servlet.*;
import java.io.IOException;
import java.util.UUID;

/**
 * @author wzt on 2019/11/15.
 * @version 1.0
 */
public class InitGlobalRequestInfoFiler implements Filter {

    private IdGenerator idGenerator = new RandomIdGenerator();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        MDC.put("traceID", idGenerator.generate());

        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            MDC.clear();
        }

    }
}