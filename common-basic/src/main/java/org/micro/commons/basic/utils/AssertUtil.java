package org.micro.commons.basic.utils;

import org.apache.commons.lang.StringUtils;

/**
 * 断言工具类
 */
public final class AssertUtil {

    public static void notNull(Object value, String msg) {
        if (value == null) {
            throw new IllegalArgumentException(msg);
        }
    }

    public static void notBlank(String value, String msg) {
        if (StringUtils.isBlank(value)) {
            throw new IllegalArgumentException(msg);
        }
    }

    public static void isTrue(boolean value, String msg) {
        if (!value) {
            throw new IllegalArgumentException(msg);
        }
    }

}
