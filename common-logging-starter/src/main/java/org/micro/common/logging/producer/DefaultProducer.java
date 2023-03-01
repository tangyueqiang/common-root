package org.micro.common.logging.producer;

import org.micro.common.logging.autoprop.LoggingProperties;
import org.micro.commons.basic.beans.Result;
import org.micro.los.common.manager.LogProducer;
import org.micro.los.common.protocol.BaseLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DefaultProducer implements LogProducer {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private LoggingProperties conf;

    public DefaultProducer(LoggingProperties conf) {
        this.conf = conf;
        logger.info("created producer {}", this.getClass());
    }

    public LoggingProperties getConf() {
        return conf;
    }

    @Override
    public void produce(BaseLog log) {
        if (log == null) {
            return;
        }
        logger.info("produce {}", log.getLine());
        if (log.getAction() != null) {
            log.getAction().doSuccess(new Result());
        }
    }

}
