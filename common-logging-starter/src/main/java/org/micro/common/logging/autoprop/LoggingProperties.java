package org.micro.common.logging.autoprop;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(LoggingProperties.PREFIX)
public class LoggingProperties {

    public static final String PREFIX = "logging";

    /**
     * 服务名称
     */
    private String application;

    /**
     * 日志过滤器配置
     */
    private FilterProperties filter;

    /**
     * 日志生产者配置
     */
    private org.micro.common.logging.autoprop.ProducerProperties producer;

    /**
     * 日志消费者配置
     */
    private ConsumerProperties consumer;

    public LoggingProperties() {
        super();
        this.filter = new FilterProperties();
        this.producer = new org.micro.common.logging.autoprop.ProducerProperties();
        this.consumer = new ConsumerProperties();
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public FilterProperties getFilter() {
        return filter;
    }

    public void setFilter(FilterProperties filter) {
        this.filter = filter;
    }

    public org.micro.common.logging.autoprop.ProducerProperties getProducer() {
        return producer;
    }

    public void setProducer(org.micro.common.logging.autoprop.ProducerProperties producer) {
        this.producer = producer;
    }

    public ConsumerProperties getConsumer() {
        return consumer;
    }

    public void setConsumer(ConsumerProperties consumer) {
        this.consumer = consumer;
    }

}
