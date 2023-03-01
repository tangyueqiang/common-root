package org.micro.los.common.protocol;


import org.micro.commons.basic.beans.BaseBean;

public class LogbackFilterLog extends SampleLog {

    private static final long serialVersionUID = 1L;

    /**
     * 服务资源命名空间
     **/
    private String namespace;

    /**
     * 服务名称
     **/
    private String applicationName;

    /**
     * 获得日志输出IP
     **/
    private String ip;

    /**
     * 获得日志输出hostname
     **/
    private String host;

    /**
     * 获得线程信息
     **/
    private String threadName;

    /**
     * 获得异常信息
     **/
    private String throwable;

    /**
     * 获得异常详细信息
     **/
    private String throwableDetail;

    /**
     * 获得调用信息
     **/
    private String location;

    /**
     * 获得traceId
     **/
    private String traceId;

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public String getThrowable() {
        return throwable;
    }

    public void setThrowable(String throwable) {
        this.throwable = throwable;
    }

    public String getThrowableDetail() {
        return throwableDetail;
    }

    public void setThrowableDetail(String throwableDetail) {
        this.throwableDetail = throwableDetail;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public static class Location extends BaseBean {

        private static final long serialVersionUID = 1L;

        private String className;

        private String methodName;

        private String fileName;

        private String LineNumber;

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }

        public String getMethodName() {
            return methodName;
        }

        public void setMethodName(String methodName) {
            this.methodName = methodName;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getLineNumber() {
            return LineNumber;
        }

        public void setLineNumber(String lineNumber) {
            LineNumber = lineNumber;
        }
    }

}
