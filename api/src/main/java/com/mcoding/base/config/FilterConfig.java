package com.mcoding.base.config;

import com.mcoding.base.filter.InitGlobalRequestInfoFiler;
import com.mcoding.base.filter.RateLimitFilter;
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
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean<>(new InitGlobalRequestInfoFiler());
        filterRegistrationBean.setOrder(2);
        return filterRegistrationBean;
    }

}

