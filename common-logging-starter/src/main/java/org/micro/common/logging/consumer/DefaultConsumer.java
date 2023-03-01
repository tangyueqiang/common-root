package org.micro.common.logging.consumer;

import org.micro.common.logging.autoprop.LoggingProperties;
import org.micro.los.common.manager.LogConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public abstract class DefaultConsumer implements LogConsumer {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private LoggingProperties conf;

    public DefaultConsumer(LoggingProperties conf) {
        this.conf = conf;
        logger.info("created consumer {}", this.getClass());
    }

    public LoggingProperties getConf() {
        return conf;
    }

}
