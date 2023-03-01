package org.micro.common.logging.autoconfig;

import org.micro.common.logging.autoprop.ConsumerProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ConsumerProperties.class)
public class ConsumerAutoConfig {

}
