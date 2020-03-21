package com.mcoding.base.config;


import brave.Tracing;
import brave.http.HttpAdapter;
import brave.http.HttpClientParser;
import brave.http.HttpServerParser;
import brave.http.HttpTracing;
import brave.propagation.B3Propagation;
import brave.propagation.ExtraFieldPropagation;
import brave.sampler.Sampler;

import brave.servlet.TracingFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.Sender;
import zipkin2.reporter.okhttp3.OkHttpSender;

import javax.servlet.Filter;
import java.util.concurrent.TimeUnit;

/**
 * @author wzt
 */
@Configuration
public class ZipkinMonitorConfig {

    @Value("${spring.application.name}")
    private String serviceName;

    @Value("${zipkin.collector.url}")
    private String collectorUrl;

    /**
     * 为了实现 dubbo rpc调用的拦截
     *
     * @return
     */
    @Bean
    public Tracing tracing() {
        Sender sender = OkHttpSender.create(collectorUrl);
        AsyncReporter reporter = AsyncReporter.builder(sender)
                .closeTimeout(1000, TimeUnit.MILLISECONDS)
                .messageTimeout(5000, TimeUnit.MILLISECONDS)
                .build();
        Tracing tracing = Tracing.newBuilder()
                .localServiceName(serviceName)
                .propagationFactory(ExtraFieldPropagation.newFactory(B3Propagation.FACTORY, "shiliew"))
                .sampler(Sampler.create(1))
                .spanReporter(reporter)
                .build();
        return tracing;
    }

    /**
     * MVC Filter，为了实现 SpringMvc 调用的拦截
     * @param tracing
     * @return
     */
    @Bean
    public Filter tracingFilter(Tracing tracing) {
        HttpTracing httpTracing = HttpTracing.create(tracing);
        httpTracing.toBuilder()
                .serverParser(new HttpServerParser() {
                    @Override
                    public <Req> String spanName(HttpAdapter<Req, ?> adapter, Req req) {
                        return adapter.path(req);
                    }
                })
                .clientParser(new HttpClientParser() {
                    @Override
                    public <Req> String spanName(HttpAdapter<Req, ?> adapter, Req req) {
                        return adapter.path(req);
                    }
                }).build();
        return TracingFilter.create(httpTracing);
    }
}