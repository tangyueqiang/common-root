package org.micro.common.logging.autoprop;

import org.springframework.boot.logging.LogLevel;

public class FilterProperties {

    /**
     * 是否启用
     */
    private boolean enabled = false;

    /**
     * 过滤级别，默认error
     */
    private LogLevel level = LogLevel.ERROR;

    /**
     * 服务资源命名空间
     */
    private String namespace;

    /**
     * 租户ID
     */
    private String tenantId = "1";

    /**
     * 租户名称
     */
    private String tenantName = "edp";

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public LogLevel getLevel() {
        return level;
    }

    public void setLevel(LogLevel level) {
        this.level = level;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
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

}
