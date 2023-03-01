package org.micro.common.logging.filter;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.classic.spi.StackTraceElementProxy;
import ch.qos.logback.classic.spi.ThrowableProxyUtil;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import org.micro.common.logging.autoconfig.ApplicationContextProvider;
import org.micro.common.logging.autoprop.LoggingProperties;
import org.micro.los.common.manager.LogProducer;
import org.micro.los.common.protocol.LogbackFilterLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

/**
 * 默认Appender
 */
public class FilterDefaultAppender extends UnsynchronizedAppenderBase<LoggingEvent> {

    protected Logger logger = LoggerFactory.getLogger(getClass());
    protected LoggingProperties conf;
    private LogProducer producer;

    @Override
    public void start() {
        //System.out.println("logging filter start ...");
        super.start();
    }

    @Override
    protected void append(LoggingEvent event) {
        if (producer == null) {
            producer = ApplicationContextProvider.getBean(LogProducer.class);
        }
        if (producer == null) {
            return;
        }

        conf = ApplicationContextProvider.getBean(LoggingProperties.class);
        // 发送过滤的日志
        producer.produce(newLog(event));
    }

    protected LogbackFilterLog newLog(LoggingEvent event) {

        LogbackFilterLog log = new LogbackFilterLog();
        log.setNamespace(conf.getFilter().getNamespace());
        log.setApplicationName(conf.getApplication());
        log.setTenantId(conf.getFilter().getTenantId());
        log.setTenantName(conf.getFilter().getTenantName());

        log.setContent(event.getFormattedMessage());
        log.setTimestamp(event.getTimeStamp());

        log.setLocation(getLocation(event).toString());
        log.setTraceId(getTraceId(event));
        log.setThreadName(event.getThreadName());
        log.setThrowable(getThrowable(event));
        log.setThrowableDetail(getThrowableDetail(event));

        try {
            log.setLevel(Level.valueOf(event.getLevel().toString()));
            log.setIp(new Address().convert(event));
            log.setHost(InetAddress.getLocalHost().getHostName());
        } catch (Exception e) {
            logger.warn("", e);
        }

        return log;
    }

    private String getTraceId(LoggingEvent event) {
        Map<String, String> mdcPropertyMap = event.getMDCPropertyMap();
        return mdcPropertyMap.get("traceId");
    }

    private String getThrowableDetail(LoggingEvent event) {
        IThrowableProxy tp = event.getThrowableProxy();
        if (tp == null)
            return "";

        StringBuilder sb = new StringBuilder(2048);
        while (tp != null) {

            ThrowableProxyUtil.subjoinFirstLine(sb, tp);

            int commonFrames = tp.getCommonFrames();
            StackTraceElementProxy[] stepArray = tp.getStackTraceElementProxyArray();
            for (int i = 0; i < stepArray.length - commonFrames; i++) {
                sb.append("\n");
                sb.append(CoreConstants.TAB);
                ThrowableProxyUtil.subjoinSTEP(sb, stepArray[i]);
            }

            if (commonFrames > 0) {
                sb.append("\n");
                sb.append(CoreConstants.TAB).append("... ").append(commonFrames).append(" common frames omitted");
            }

            sb.append("\n");

            tp = tp.getCause();
        }
        return sb.toString();
    }

    private String getThrowable(LoggingEvent event) {
        IThrowableProxy tp = event.getThrowableProxy();
        if (tp == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder(2048);
        sb.append(tp.getClassName());
        return sb.toString();
    }

    private LogbackFilterLog.Location getLocation(LoggingEvent event) {
        LogbackFilterLog.Location location = new LogbackFilterLog.Location();
        StackTraceElement[] cda = event.getCallerData();
        if (cda != null && cda.length > 0) {
            StackTraceElement immediateCallerData = cda[0];
            location.setClassName(immediateCallerData.getClassName());
            location.setMethodName(immediateCallerData.getMethodName());
            location.setFileName(immediateCallerData.getFileName());
            location.setLineNumber(String.valueOf(immediateCallerData.getLineNumber()));
        }
        return location;
    }

    static class Address extends ClassicConverter {
        @Override
        public String convert(ILoggingEvent iLoggingEvent) {
            try {
                return InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
