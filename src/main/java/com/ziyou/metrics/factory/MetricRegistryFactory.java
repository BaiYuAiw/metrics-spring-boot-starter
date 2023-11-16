package com.ziyou.metrics.factory;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Reporter;
import com.ziyou.metrics.common.MetricReportType;
import com.ziyou.metrics.config.MetricProperties;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author libo
 * @date 2023/11/16
 */
@Configuration
public class MetricRegistryFactory {
    private String reportType;
    private TimeUnit ratesUnit;
    private TimeUnit durationUnit;
    private TimeUnit startUnit;
    private Integer startTime;
    private Reporter reporter;

    public final static MetricRegistry registry = new MetricRegistry();

    public MetricRegistryFactory(MetricProperties properties){
        this.reportType = properties.getReportType();
        this.ratesUnit = properties.getRatesUnit();
        this.durationUnit = properties.getDurationUnit();
        this.startUnit = properties.getStartUnit();
        this.startTime = properties.getStartTime();

    }

    public Reporter getReporter(){
        if(reporter == null){
            synchronized (MetricRegistryFactory.class){
                if(reporter == null){
                    if(MetricReportType.CONSOLE.getReportType().equals(reportType)){
                        ConsoleReporter consoleReporter = ConsoleReporter
                                .forRegistry(registry)
                                .convertRatesTo(ratesUnit)
                                .convertDurationsTo(durationUnit)
                                .build();
                        consoleReporter.start(startTime, startUnit);
                        reporter = consoleReporter;
                    }
                }
            }
        }
        return reporter;
    }


}
