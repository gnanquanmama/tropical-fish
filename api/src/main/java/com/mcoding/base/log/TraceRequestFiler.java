package com.mcoding.base.log;

import com.mcoding.common.id.IdGenerator;
import com.mcoding.common.id.RandomIdGenerator;
import com.mcoding.common.util.MdcConstants;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author wzt on 2019/11/15.
 * @version 1.0
 */
@Slf4j
public class TraceRequestFiler implements Filter {

    private IdGenerator idGenerator = new RandomIdGenerator();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        String traceId = idGenerator.generate();
        MDC.put(MdcConstants.TRACE_ID, traceId);
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            MDC.clear();
        }
    }
}