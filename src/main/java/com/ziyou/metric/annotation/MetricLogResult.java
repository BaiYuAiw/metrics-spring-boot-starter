package com.ziyou.metric.annotation;

import java.lang.annotation.*;

/**
 * 统计结果
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface MetricLogResult {
    String key();

    boolean exception() default true;

    boolean nullAsFail() default true;
}
