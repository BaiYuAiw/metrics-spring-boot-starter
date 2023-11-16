package com.ziyou.metrics.annotation;

import java.lang.annotation.*;

/**
 * 默认精度为微秒，支持纳秒
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface MetricLogLatency {
    String key();

    boolean nano() default false;
}
