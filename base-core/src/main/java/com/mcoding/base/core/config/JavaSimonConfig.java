package com.mcoding.base.core.config;

import org.javasimon.console.SimonConsoleServlet;
import org.javasimon.javaee.SimonServletFilter;
import org.javasimon.spring.MonitoredMeasuringPointcut;
import org.javasimon.spring.MonitoringInterceptor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wzt on 2019/11/14.
 * @version 1.0
 */
@Configuration
public class JavaSimonConfig {

    @Bean(name = "monitoringAdvisor")
    public DefaultPointcutAdvisor monitoringAdvisor() {
        DefaultPointcutAdvisor monitoringAdvisor = new DefaultPointcutAdvisor();
        monitoringAdvisor.setAdvice(new MonitoringInterceptor());
        monitoringAdvisor.setPointcut(new MonitoredMeasuringPointcut());
        return monitoringAdvisor;
    }

    @Bean
    public ServletRegistrationBean dispatcherRegistration() {
        ServletRegistrationBean registration = new ServletRegistrationBean<>(new SimonConsoleServlet(), "/javasimon/*");
        registration.addInitParameter("url-prefix", "/javasimon");
        return registration;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean<>(new SimonServletFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

}