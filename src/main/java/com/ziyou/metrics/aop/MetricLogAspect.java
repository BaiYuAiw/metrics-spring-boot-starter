package com.ziyou.metrics.aop;

import com.ziyou.metrics.annotation.MetricLogLatency;
import com.ziyou.metrics.annotation.MetricLogResult;
import com.ziyou.metrics.common.MetricLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author libo
 * @date 2023/11/16
 */

@Component
@Aspect
public class MetricLogAspect {
    private static final String SUCCESS = "success";
    private static final String FAILED = "failed";

    @Autowired
    private MetricLog metricLog;

    @Around("@annotation(com.ziyou.metrics.annotation.MetricLogLatency)")
    public Object metricLogLatency(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        MetricLogLatency metricLogLatency = signature.getMethod().getAnnotation(MetricLogLatency.class);
        long start, end;
        if (metricLogLatency.nano()) {
            start = System.nanoTime();
        } else {
            start = System.currentTimeMillis();
        }
        Object result = point.proceed();
        if (metricLogLatency.nano()) {
            end = System.nanoTime();
        } else {
            end = System.currentTimeMillis();
        }
        String key = metricLogLatency.key();
        metricLog.log(key, end - start);
        return result;
    }

    @AfterReturning(pointcut = "@annotation(com.ziyou.metrics.annotation.MetricLogResult)", returning = "returnValue")
    public void metricLogResult(JoinPoint point, Object returnValue) {
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        MetricLogResult metricLogResult = methodSignature.getMethod().getAnnotation(MetricLogResult.class);
        String key = metricLogResult.key();
        boolean nullAsFail = metricLogResult.nullAsFail();
        String val;
        if (nullAsFail && returnValue == null) {
            val = FAILED;
        } else {
            val = SUCCESS;
        }
        metricLog.log(key, val);
    }

    @AfterThrowing(pointcut = "@annotation(com.ziyou.metrics.annotation.MetricLogResult)")
    public void metricLogResultException(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        MetricLogResult metricLogResult = methodSignature.getMethod().getAnnotation(MetricLogResult.class);
        String key = metricLogResult.key();
        boolean exp = metricLogResult.exception();
        if (exp) {
            metricLog.log(key, FAILED);
        }
    }

}
