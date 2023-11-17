package com.ziyou.metric.common;

/**
 * @author libo
 * @date 2023/11/16
 */
public enum MetricReportType {
    GRAPHITE("graphite"),
    CONSOLE("console");
    private String reportType;

    private MetricReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getReportType() {
        return reportType;
    }

}
