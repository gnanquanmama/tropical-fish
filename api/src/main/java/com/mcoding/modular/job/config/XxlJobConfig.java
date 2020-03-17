package com.mcoding.modular.job.config;

import com.xxl.job.core.executor.impl.XxlJobSpringExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author wzt on 2019/11/20.
 * @version 1.0
 */
@Slf4j
@Configuration
public class XxlJobConfig {

    @Resource
    private XxlJobPropertiesConfig xxlJobPropertiesConfig;

    @Bean
    public XxlJobSpringExecutor xxlJobExecutor() {
        log.info(">>>>>>>>>>> xxl-job config init.");
        XxlJobSpringExecutor xxlJobSpringExecutor = new XxlJobSpringExecutor();
        xxlJobSpringExecutor.setAdminAddresses(xxlJobPropertiesConfig.getAdmin_addresses());
        xxlJobSpringExecutor.setAppName(xxlJobPropertiesConfig.getAppname());

        return xxlJobSpringExecutor;
    }

}
