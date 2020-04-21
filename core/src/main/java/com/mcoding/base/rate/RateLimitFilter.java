package com.mcoding.base.rate;

import com.alibaba.fastjson.JSON;
import com.google.common.util.concurrent.RateLimiter;
import com.mcoding.common.util.rest.ResponseResult;

import javax.servlet.*;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author wzt on 2019/11/23.
 * @version 1.0
 */
public class RateLimitFilter implements Filter {

    private static RateLimiter rateLimiter = RateLimiter.create(1000);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {

        if (rateLimiter.tryAcquire()) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            PrintWriter printWriter = servletResponse.getWriter();
            printWriter.print(JSON.toJSONString(ResponseResult.fail("限流中，请稍后重试")));
            printWriter.flush();
            printWriter.close();
        }

    }
}
