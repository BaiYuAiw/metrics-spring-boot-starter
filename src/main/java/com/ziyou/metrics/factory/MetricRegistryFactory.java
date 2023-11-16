package com.ziyou.metrics.factory;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Reporter;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;
import com.ziyou.metrics.common.MetricReportType;
import com.ziyou.metrics.config.GraphiteConfig;
import com.ziyou.metrics.config.MetricProperties;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;
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
    private String serverName;

    private Reporter reporter;

    private GraphiteConfig graphiteConfig;

    public final static MetricRegistry registry = new MetricRegistry();

    public MetricRegistryFactory(MetricProperties properties) {
        this.reportType = properties.getReportType();
        this.ratesUnit = properties.getRatesUnit();
        this.durationUnit = properties.getDurationUnit();
        this.startUnit = properties.getStartUnit();
        this.startTime = properties.getStartTime();

        this.graphiteConfig = properties.getGraphiteConfig();

    }

    public Reporter getReporter() {
        if (reporter == null) {
            synchronized (MetricRegistryFactory.class) {
                if (reporter == null) {
                    if (MetricReportType.CONSOLE.getReportType().equals(reportType)) {
                        ConsoleReporter consoleReporter = ConsoleReporter
                                .forRegistry(registry)
                                .convertRatesTo(ratesUnit)
                                .convertDurationsTo(durationUnit)
                                .build();
                        consoleReporter.start(startTime, startUnit);
                        reporter = consoleReporter;
                    } else if (MetricReportType.GRAPHITE.getReportType().equals(reportType)) {
                        GraphiteReporter graphiteReporter = GraphiteReporter
                                .forRegistry(registry)
                                .prefixedWith(serverName+"."+reportType)
                                .convertRatesTo(ratesUnit)
                                .convertDurationsTo(durationUnit)
                                .filter(MetricFilter.ALL)
                                .build(new Graphite(new InetSocketAddress(graphiteConfig.getIpAddress(), graphiteConfig.getPort())));
                        graphiteReporter.start(startTime, startUnit);
                        reporter = graphiteReporter;
                    }
                }
            }
        }
        return reporter;
    }


}
