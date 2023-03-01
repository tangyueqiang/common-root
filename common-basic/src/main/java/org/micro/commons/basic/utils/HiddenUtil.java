package org.micro.commons.basic.utils;

import org.apache.commons.lang.StringUtils;

/**
 * 字符串隐藏处理工具类
 */
public final class HiddenUtil {

    public static final String HIDDEN_PASSWORD = "******";

    private HiddenUtil() {
    }

    /**
     * 隐藏邮箱字符串
     *
     * @param email       原始邮箱字符串
     * @param retainStart 保留@符号前n位
     * @return 隐藏处理后字符串
     */
    public static String hiddenEmail(String email, int retainStart) {

        if (StringUtils.isBlank(email) || !email.contains("@")) {
            return email;
        }

        retainStart = Math.max(1, retainStart);
        int at = email.indexOf("@");

        String start = email.substring(0, at);
        if (retainStart >= start.length()) {
            return email;
        }

        String end = email.substring(at);
        start = start.substring(0, retainStart);

        email = start.concat("***").concat(end);
        return email;
    }

    /**
     * 隐藏电话号码中间4位
     *
     * @param phone 原始电话号码
     * @return 隐藏处理后字符串
     */
    public static String hiddenPhone(String phone) {
        if (StringUtils.isBlank(phone) || phone.length() < 8) {
            return phone;
        }

        String start = StringUtils.substring(phone, 0, -8);
        String end = StringUtils.substring(phone, -4);

        phone = start.concat("****").concat(end);
        return phone;
    }

}
