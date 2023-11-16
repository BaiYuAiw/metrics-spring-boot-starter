package com.ziyou;

import com.codahale.metrics.Reporter;
import com.ziyou.metrics.annotation.MetricLogLatency;
import com.ziyou.metrics.annotation.MetricLogResult;
import com.ziyou.metrics.factory.MetricRegistryFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author libo
 * @date 2023/11/15
 */

@RestController
@SpringBootApplication
public class MetricsLauncher {
    public static void main(String[] args) {
        SpringApplication.run(MetricsLauncher.class,args);
    }
    @Resource
    private MetricRegistryFactory factory;


    @RequestMapping("/test")
    @MetricLogLatency(key = "testLatency")
    @MetricLogResult(key = "testResult")
    public Object test(){
        int num = ThreadLocalRandom.current().nextInt(10);
        System.out.println("=====  "+num+"  =======================");
        if(num%2 == 0)return null;
        return new Object();
    }
}
