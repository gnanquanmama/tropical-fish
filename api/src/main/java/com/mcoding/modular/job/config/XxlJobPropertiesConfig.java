package com.mcoding.modular.job.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author wzt on 2019/11/18.
 * @version 1.0
 */
@ConfigurationProperties(prefix = "xxl.job.executor")
@Component
@Data
public class XxlJobPropertiesConfig {

    private String admin_addresses;
    private String appname;
    private String ip;
    private Integer port;
    private String accessToken;
    private String logpath;
    private Integer logretentiondays;

}
