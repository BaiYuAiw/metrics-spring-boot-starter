package com.ziyou.metrics.common;

/**
 * 统计类型的枚举类
 *
 * @author libo
 * @date 2023/11/16
 */
public enum MetricAttr {
    MAX("max"),
    MIN("min"),
    MEAN("mean"),
    STDDEV("stddev"),
    P50("p50"),
    P75("p75"),
    P90("p90"),
    P95("p95"),
    P98("p98"),
    P99("p99"),
    P999("p999"),
    COUNT("count"),
    M1_RATE("m1_rate"),
    M5_RATE("m5_rate"),
    M15_RATE("m15_rate"),
    MEAN_RATE("mean_rate");
    private final String code;

    private MetricAttr(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
