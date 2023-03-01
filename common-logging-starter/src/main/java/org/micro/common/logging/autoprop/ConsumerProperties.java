package org.micro.common.logging.autoprop;

import org.micro.common.logging.beans.ConsumerStoreType;
import org.micro.common.logging.beans.ProducerType;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(ConsumerProperties.PREFIX)
public class ConsumerProperties {

    public static final String PREFIX = "logging.consumer";

    /**
     * 日志生产者类型
     */
    private ProducerType type = ProducerType.none;

    /**
     * 日志消费者存储类型
     */
    private List<ConsumerStoreType> storeTypes;

    public ConsumerProperties() {
        super();
        this.storeTypes = new ArrayList<>();
    }

    public ProducerType getType() {
        return type;
    }

    public void setType(ProducerType type) {
        this.type = type;
    }

    public List<ConsumerStoreType> getStoreTypes() {
        return storeTypes;
    }

    public void setStoreTypes(List<ConsumerStoreType> storeTypes) {
        this.storeTypes = storeTypes;
    }

}
