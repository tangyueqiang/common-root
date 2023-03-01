package org.micro.common.logging.producer;

import org.micro.common.logging.autoconfig.ApplicationContextProvider;
import org.micro.common.logging.autoprop.LoggingProperties;
import org.micro.commons.basic.beans.Result;
import org.micro.los.common.protocol.BaseLog;
import org.micro.los.common.protocol.LogbackFilterLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;


public class RabbitProducer extends DefaultProducer {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    private RabbitMessagingTemplate rabbitTemplate;

    public RabbitProducer(LoggingProperties conf) {
        super(conf);
    }

    @Override
    public void produce(BaseLog log) {

        if (rabbitTemplate == null) {
            rabbitTemplate = ApplicationContextProvider.getBean(RabbitMessagingTemplate.class);
        }

        if (log == null) {
            return;
        }
        String prefix = getConf().getApplication();
        if (log instanceof LogbackFilterLog) {
            prefix = null;
        }

        rabbitTemplate.convertAndSend(log.getQueueName(prefix), log.toString());
        if (log.getAction() != null) {
            log.getAction().doSuccess(new Result(log.getQueueName(prefix)));
        }
    }

    public RabbitMessagingTemplate getRabbitTemplate() {
        return rabbitTemplate;
    }

    public void setRabbitTemplate(RabbitMessagingTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

}
