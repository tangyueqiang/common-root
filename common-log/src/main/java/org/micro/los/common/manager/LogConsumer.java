package org.micro.los.common.manager;

import org.micro.los.common.protocol.BaseLog;
import org.micro.los.common.protocol.FinishedLog;

/**
 * 日志消费者
 */
public interface LogConsumer {

    /**
     * 消费日志
     *
     * @param log 日志
     */
    void consume(BaseLog log);

    /**
     * 完成日志发起打包日志
     *
     * @param log 完成日志
     * @return 日志包对象
     */
    Object pkgLogs(FinishedLog log);
}
