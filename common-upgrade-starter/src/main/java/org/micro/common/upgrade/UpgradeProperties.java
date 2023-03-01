package org.micro.common.upgrade;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;

@ConfigurationProperties(UpgradeProperties.PREFIX)
public class UpgradeProperties {

    public static final String PREFIX = "upgrade";

    /**
     * 是否开启升级程序(默认false关闭)
     */
    private boolean enabled = false;

    /**
     * 版本
     */
    private String version;

    /**
     * 应用名称
     */
    private String applicationName = "Application";

    /**
     * 升级脚本文件
     */
    private Resource upgradeScript;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public Resource getUpgradeScript() {
        return upgradeScript;
    }

    public void setUpgradeScript(Resource upgradeScript) {
        this.upgradeScript = upgradeScript;
    }

}
