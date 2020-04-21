package com.mcoding.base.config;

import com.mcoding.base.log.TraceRequestFiler;
import com.mcoding.base.doc.filter.MethodInvokeTreeFiler;
import com.mcoding.base.rate.RateLimitFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wzt on 2019/11/15.
 * @version 1.0
 */
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean rateLimitFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean<>(new RateLimitFilter());
        filterRegistrationBean.setOrder(1);
        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean initBaseDataFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean<>(new TraceRequestFiler());
        filterRegistrationBean.setOrder(2);
        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean initInvokeTreeFiler() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean<>(new MethodInvokeTreeFiler());
        filterRegistrationBean.setOrder(3);
        return filterRegistrationBean;
    }

}

