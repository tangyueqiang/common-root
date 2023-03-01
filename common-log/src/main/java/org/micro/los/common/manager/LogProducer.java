package org.micro.los.common.manager;

import org.micro.los.common.protocol.BaseLog;

/**
 * 日志生产者
 */
public interface LogProducer {

    /**
     * 生产日志
     *
     * @param log 日志
     */
    void produce(BaseLog log);
}
