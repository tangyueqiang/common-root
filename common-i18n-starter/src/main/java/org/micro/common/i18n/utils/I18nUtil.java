package org.micro.common.i18n.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.micro.common.i18n.I18nException;

import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class I18nUtil {

    public I18nUtil() {
    }

    /**
     * 生成I18nCodes.java
     *
     * @param propertiesFile i18n/messages_en_US.properties位置
     * @return string
     * @throws Exception 异常
     */
    public static String generateI18nCodesJava(String propertiesFile) throws Exception {

        File file = new File("I18nCodes.java");
        propertiesFile = StringUtils.isBlank(propertiesFile)
                ? "i18n/messages_zh_CN.properties" : propertiesFile;

        String linesep = "\n";
        String tabstr = "\t";
        InputStreamReader reader = null;
        try {
            reader = new InputStreamReader(Objects.requireNonNull(I18nUtil.class.getClassLoader().getResourceAsStream(propertiesFile)), StandardCharsets.UTF_8);
            Properties prop = new Properties();
            prop.load(reader);

            // 写JAVA文件
            StringBuilder content = new StringBuilder();
            content.append("package com.gouuse.datahub.template.service;").append(linesep);
            content.append(linesep);
            content.append("/** I18n Message codes **/").append(linesep);
            content.append("public interface I18nCodes {").append(linesep);
            content.append(tabstr).append(linesep);

            // codes start
            List<String> keys = new ArrayList<>();
            prop.keySet().forEach(key -> keys.add(key.toString()));
            keys.sort(Comparator.naturalOrder());

            for (String key : keys) {
                if (!StringUtils.startsWithIgnoreCase(key, "commons.")) {
                    continue;
                }

                content.append(tabstr).append("/** ").append(prop.getProperty(key, ""))
                        .append(" **/").append(linesep);

                String[] arr = key.split("\\.");
                content.append(tabstr).append("String ").append(arr[arr.length - 1]);
                content.append(" = \"");
                content.append(key);
                content.append("\";").append(linesep);
                content.append(tabstr).append(linesep);
            }
            // codes end
            content.append("}").append(linesep);

            FileUtils.writeStringToFile(file, content.toString(), StandardCharsets.UTF_8);
        } finally {

            IOUtils.closeQuietly(reader);
        }

        return file.getAbsolutePath();
    }

    public static int getI18nCode(String i18nCode) {
        int charLen = 4;
        if (i18nCode == null || i18nCode.length() < charLen) {
            return -1;
        }

        String[] arr = i18nCode.split("\\.");
        i18nCode = arr[arr.length - 1];

        return Integer.parseInt(i18nCode.substring(charLen));
    }

    public static void assertContainsIgnoreCase(I18nException ex, String str, String... range) {
        assertContains(true, ex, str, range);
    }

    public static void assertContains(I18nException ex, String str, String... range) {
        assertContains(false, ex, str, range);
    }

    private static void assertContains(boolean ignoreCase, I18nException ex, String str, String... range) {
        boolean contains = false;
        for (String e : range) {
            if (str == null && e == null || contains) {
                return;
            }

            if (str == null) {
                continue;
            }

            contains = (ignoreCase ? str.equalsIgnoreCase(e) : str.equals(e));
        }

        if (!contains) {
            throw ex;
        }
    }

}
