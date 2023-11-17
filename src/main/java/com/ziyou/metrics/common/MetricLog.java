package com.ziyou.metrics.common;

import com.codahale.metrics.*;
import com.ziyou.metrics.factory.MetricRegistryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author libo
 * @date 2023/11/16
 */

@Component
public class MetricLog {
    private static final Logger logger = LoggerFactory.getLogger(MetricLog.class);


    private Set<MetricAttr> attrSet;
    private Map<String, Meter> meterMap;
    private Map<String, Timer> timerMap;
    private Map<String, Gauge> gaugeMap;

    @PostConstruct
    public void init() {
        if (attrSet == null) {
            attrSet = new HashSet<>();
            attrSet.add(MetricAttr.MAX);
            attrSet.add(MetricAttr.MIN);
            attrSet.add(MetricAttr.MEAN);
        }
        meterMap = new ConcurrentHashMap<>();
        timerMap = new ConcurrentHashMap<>();
        gaugeMap = new ConcurrentHashMap<>();
    }

    public void log(String key, long value) {
        Timer timer = timerMap.get(key);
        if (timer == null) {
            synchronized (this) {
                timer = timerMap.get(key);
                if (timer == null) {
                    timerMap.put(key, MetricRegistryFactory.registry.timer(MetricRegistry.name(key)));
                }
                timer = timerMap.get(key);
            }
        }
        timer.update(value, TimeUnit.MILLISECONDS);
    }

    public void log(String key, String value) {
        String metricName = key + "." + value;
        Meter meter = meterMap.get(metricName);
        if (meter == null) {
            synchronized (this) {
                meter = meterMap.get(metricName);
                if (meter == null) {
                    meterMap.put(metricName, MetricRegistryFactory.registry.meter(MetricRegistry.name(metricName)));
                }
                meter = meterMap.get(metricName);
            }
        }
        meter.mark();
    }
}
