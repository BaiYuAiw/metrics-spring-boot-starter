package com.ziyou.metric.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author libo
 * @date 2023/11/16
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "ziyou.metric.graphite")
public class GraphiteConfig {
    private String ipAddress="127.0.0.1";
    private Integer port=2003;
}
