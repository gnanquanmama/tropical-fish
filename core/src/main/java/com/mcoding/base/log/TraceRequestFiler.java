package com.mcoding.base.log;

import com.mcoding.common.util.constant.MdcConstants;
import com.mcoding.common.util.id.IdGenerator;
import com.mcoding.common.util.id.RandomIdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
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
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            log.info("请求REQUEST_URL={}|METHOD={}", httpServletRequest.getRequestURL(), httpServletRequest.getMethod());

            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            MDC.clear();
        }
    }
}