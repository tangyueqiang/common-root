package org.micro.common.logging.autoconfig;

import org.micro.common.logging.autoprop.LoggingProperties;
import org.micro.common.logging.autoprop.ProducerProperties;
import org.micro.common.logging.producer.DefaultProducer;
import org.micro.common.logging.producer.HttpRestProducer;
import org.micro.common.logging.producer.RabbitProducer;
import org.micro.los.common.manager.LogProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ProducerProperties.class)
public class ProducerAutoConfig {

    @Autowired
    private LoggingProperties conf;

    @Bean
    @ConditionalOnMissingBean(LogProducer.class)
    @ConditionalOnProperty(prefix = ProducerProperties.PREFIX, value = "type", havingValue = "none")
    public LogProducer defaultProducer() {
        return new DefaultProducer(conf);
    }

    @Bean
    @ConditionalOnMissingBean(LogProducer.class)
    @ConditionalOnProperty(prefix = ProducerProperties.PREFIX, value = "type", havingValue = "httprest")
    public LogProducer httprestProducer() {
        return new HttpRestProducer(conf);
    }

    @Bean
    @ConditionalOnMissingBean(LogProducer.class)
    @ConditionalOnProperty(prefix = ProducerProperties.PREFIX, value = "type", havingValue = "rabbit")
    public LogProducer rabbitProducer() {
        RabbitProducer producer = new RabbitProducer(conf);
        return producer;
    }


}
