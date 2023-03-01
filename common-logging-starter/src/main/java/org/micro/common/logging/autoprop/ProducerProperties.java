package org.micro.common.logging.autoprop;

import org.micro.common.logging.beans.ProducerType;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(ProducerProperties.PREFIX)
public class ProducerProperties {

    public static final String PREFIX = "logging.producer";

    /**
     * 日志生产者类型
     */
    private ProducerType type = ProducerType.none;
    /**
     * http rest api配置
     */
    private HttpRestProperties httprest;

    public ProducerType getType() {
        return type;
    }

    public void setType(ProducerType type) {
        this.type = type;
    }

    public HttpRestProperties getHttprest() {
        return httprest;
    }

    public void setHttprest(HttpRestProperties httprest) {
        this.httprest = httprest;
    }

}
