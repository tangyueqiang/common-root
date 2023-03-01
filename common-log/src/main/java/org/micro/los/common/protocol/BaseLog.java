package org.micro.los.common.protocol;

import org.apache.commons.lang.StringUtils;
import org.micro.commons.basic.beans.BaseBean;
import org.micro.commons.basic.utils.CamelCaseUtils;
import org.micro.los.common.manager.Action;
import org.slf4j.event.Level;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.UUID;

/**
 * 日志基类
 */
public class BaseLog extends BaseBean {

    /**
     * 索引列
     **/
    public static final String INDEX_ID = "id";
    /**
     * 索引列
     **/
    public static final String INDEX_TIMESTAMP = "timestamp";
    private static final long serialVersionUID = 1L;
    /**
     * 日志ID
     **/
    private String id;

    /**
     * 租户ID
     **/
    private String tenantId;

    /**
     * 租户名称
     **/
    private String tenantName;

    /**
     * 日志级别
     **/
    private Level level;

    /**
     * 日志业务类型
     **/
    private String buzz = "default";

    /**
     * 日志标题
     **/
    private String title;

    /**
     * 日志内容
     **/
    private String content;

    /*** 指定更新字段(不指定时插入) **/
    private String ukeys;

    /**
     * 日志时间
     **/
    private long timestamp;

    /**
     * 类名
     **/
    private String className;

    private transient Action action;

    public BaseLog() {
        super();
        this.id = UUID.randomUUID().toString();
        this.timestamp = System.currentTimeMillis();
        this.className = getClass().getName();
        this.level = Level.INFO;
    }

    public BaseLog(String title, String content) {
        this();
        this.title = title;
        this.content = content;
    }

    public BaseLog(String buzz, String title, String content) {
        this();
        this.buzz = buzz;
        this.title = title;
        this.content = content;
    }

    public BaseLog(Level level, String title, String content) {
        this();
        this.level = level;
        this.title = title;
        this.content = content;
    }

    public BaseLog(Level level, String buzz, String title, String content) {
        this();
        this.buzz = buzz;
        this.level = level;
        this.title = title;
        this.content = content;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    /**
     * 带格式的日志内容
     *
     * @param format 格式参数占位，例: {0},{1}
     * @param args   参数
     */
    public void setContentArgs(String format, String... args) {
        this.content = MessageFormat.format(format, (Object[]) args);
    }

    /**
     * 转换为行
     *
     * @return string
     */
    public String getLine() {
        String line = "%s\t[%s]\t[%s]\t%s";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        line = String.format(line,
                format.format(timestamp),
                level.name(),
                title,
                content);
        return line;
    }

    /**
     * 队列名称
     **/
    public String getQueueName(String prefix) {

        String queue = this.getClass().getSimpleName();
        queue = CamelCaseUtils.toUnderlineString(queue);
        queue = queue.replace("_", ".");

        if (StringUtils.isBlank(prefix)) {
            return queue;
        }

        prefix = prefix.replaceAll("[_\\-]", ".");
        queue = String.format("%s.%s", prefix, queue);

        return queue;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public String getBuzz() {
        return buzz;
    }

    public void setBuzz(String buzz) {
        this.buzz = buzz;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUkeys() {
        return ukeys;
    }

    public void setUkeys(String ukeys) {
        this.ukeys = ukeys;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

}
