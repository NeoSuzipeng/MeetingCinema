package com.stylefeng.guns.rest.config;

import brave.context.slf4j.MDCScopeDecorator;
import brave.propagation.CurrentTraceContext;
import brave.spring.beans.TracingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.okhttp3.OkHttpSender;

/**
 * @author : su
 * @program :
 * @description : 链路监控配置
 **/
@Configuration
public class TraceConfig {

    @Bean
    public TracingFactoryBean getTracingBean(){
        TracingFactoryBean tracingFactoryBean = new TracingFactoryBean();
        tracingFactoryBean.setLocalServiceName("gateway");
        tracingFactoryBean.setSpanReporter(AsyncReporter.create(OkHttpSender.create("http://localhost:9411/api/v2/spans")));

        return tracingFactoryBean;
    }

}
