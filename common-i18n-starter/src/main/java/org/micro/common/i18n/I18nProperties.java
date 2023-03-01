package org.micro.common.i18n;

import java.util.Properties;

public class I18nProperties extends Properties {

    private static final long serialVersionUID = 1L;

    @Override
    public String getProperty(String key, String arg) {
        String value = getProperty(key);
        if (value != null) {
            value = String.format(value, arg);
        }
        return value;
    }

    /**
     * 带参数
     *
     * @param key  key
     * @param args 参数
     * @return String
     */
    public String getProperty(String key, Object... args) {
        String value = getProperty(key);
        if (value != null) {
            value = String.format(value, args);
        }
        return value;
    }


}
