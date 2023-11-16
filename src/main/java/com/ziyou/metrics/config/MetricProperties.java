package com.ziyou.metrics.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


import java.util.concurrent.TimeUnit;

/**
 * @author libo
 * @date 2023/11/16
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "ziyou.metric")
public class MetricProperties {
    @Autowired
    private GraphiteConfig graphiteConfig;

    private String reportType = "console";
    private TimeUnit ratesUnit = TimeUnit.SECONDS;
    private TimeUnit durationUnit = TimeUnit.SECONDS;
    private TimeUnit startUnit = TimeUnit.SECONDS;
    private Integer startTime = 10;
    private String serverName = "report";
}
