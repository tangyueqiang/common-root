package org.micro.common.upgrade;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.Date;

/**
 * 版本升级数据模型
 */
public class Upgrade implements Serializable {

    public static final String TABLE = "upgrade";
    public static final String COLUMN = "app_name, app_version, upgrade_time";
    public static final String DDL = "CREATE TABLE upgrade ( "
            + "app_name varchar(255) NOT NULL, "
            + "app_version varchar(50) NOT NULL PRIMARY KEY, "
            + "upgrade_time varchar(255) NOT NULL)";
    private static final long serialVersionUID = 1L;
    private String appName;

    private String appVersion;

    private Date upgradeTime;

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Date getUpgradeTime() {
        return upgradeTime;
    }

    public void setUpgradeTime(Date upgradeTime) {
        this.upgradeTime = upgradeTime;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
