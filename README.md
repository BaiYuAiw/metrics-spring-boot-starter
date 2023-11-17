### metric-spring-boot-starter
将metric封装成一个starter更好的集成springboot</br>
目前只支持两种report **console与graphite**
### 使用步骤
1. 下载代码 
2. maven install 
3. 引入依赖即可
### 参数配置
其中每个参数都具有默认值,可自行查看META-INF下的文件additional-spring-configuration-metadata.json
- **ziyou.metric.report-type**: report类型,目前支持**console、graphite**
- **ziyou.metric.rates-unit**: report中配置的单位,下面几个都是类似的,不再具体介绍
- **ziyou.metric.duration-unit**
- **ziyou.metric.start-unit**
- **ziyou.metric.start-time**
- **ziyou.metric.server-name**: 服务名,这个名字主要是为了方便配置,以点(.)分割形成多级目录
- **ziyou.metric.graphite.ip-address**: graphite的ip
- **ziyou.metric.graphite.port**:graphite的port

两个注解<br>

- **@MetricLogLatency**:统计耗时
- **@MetricLogResult**:：统计成功与失败

**nano**: 使用微妙还是纳秒统计 <br>
**nullAsFail**: 返回数据为空时，算成功还是失败


### 风险与BUG
可能存在bug，使用期间有bug可提issue
thanks！！！
