package org.micro.commons.basic.meta;

import org.apache.commons.lang.StringUtils;

/**
 * 表类型
 */
public enum TableType {
    /**
     * 表
     **/
    TABLE("TABLE"),
    /**
     * 视图
     **/
    VIEW("VIEW"),
    /**
     * 系统表
     **/
    SYSTEMTABLE("SYSTEM TABLE"),
    /**
     * 全局临时表
     **/
    GLOBALTEMPORARY("GLOBAL TEMPORARY"),
    /**
     * 本地临时表
     **/
    LOCALTEMPORARY("LOCAL TEMPORARY"),
    /**
     * 别名
     **/
    ALIAS("ALIAS"),
    /**
     * 同义词
     **/
    SYNONYM("SYNONYM");

    /**
     * type schema
     **/
    private String typeName;

    TableType(String typeName) {
        this.typeName = typeName;
    }

    public static TableType valueOfByName(String name) {
        TableType type = null;
        if (StringUtils.isBlank(name)) {
            return null;
        }
        switch (name) {
            case "TABLE":
                type = TABLE;
                break;
            case "VIEW":
                type = VIEW;
                break;
            case "SYSTEM TABLE":
                type = SYSTEMTABLE;
                break;
            case "GLOBAL TEMPORARY":
                type = GLOBALTEMPORARY;
                break;
            case "LOCAL TEMPORARY":
                type = LOCALTEMPORARY;
                break;
            case "ALIAS":
                type = ALIAS;
                break;
            case "SYNONYM":
                type = SYNONYM;
                break;

            default:
                break;
        }
        return type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return this.typeName;
    }


}
