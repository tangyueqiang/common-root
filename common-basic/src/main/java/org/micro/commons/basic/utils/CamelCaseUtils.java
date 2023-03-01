package org.micro.commons.basic.utils;

/**
 * 驼峰命名与下划线转换工具类
 */
public final class CamelCaseUtils {

    private static final char SEPARATOR = '_';

    private CamelCaseUtils() {
    }

    /**
     * 驼峰转下划线
     *
     * @param camel camel
     * @return String
     */
    public static String toUnderlineString(String camel) {
        if (camel == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        boolean upperCase = false;
        for (int i = 0; i < camel.length(); i++) {
            char c = camel.charAt(i);

            boolean nextUpperCase = true;

            if (i < (camel.length() - 1)) {
                nextUpperCase = Character.isUpperCase(camel.charAt(i + 1));
            }

            if (Character.isUpperCase(c)) {
                if (!upperCase || !nextUpperCase) {
                    if (i > 0) sb.append(SEPARATOR);
                }
                upperCase = true;
            } else {
                upperCase = false;
            }

            sb.append(Character.toLowerCase(c));
        }

        return sb.toString();
    }

    /**
     * 转换为驼峰命名
     *
     * @param inputString             inputString
     * @param firstCharacterUppercase firstCharacterUppercase
     * @return String
     */
    public static String toCamelCaseString(String inputString,
                                           boolean firstCharacterUppercase) {
        StringBuilder sb = new StringBuilder();

        boolean nextUpperCase = false;
        for (int i = 0; i < inputString.length(); i++) {
            char c = inputString.charAt(i);

            switch (c) {
                case '_':
                case '-':
                case '@':
                case '$':
                case '#':
                case ' ':
                case '/':
                case '&':
                    if (sb.length() > 0) {
                        nextUpperCase = true;
                    }
                    break;

                default:
                    if (nextUpperCase) {
                        sb.append(Character.toUpperCase(c));
                        nextUpperCase = false;
                    } else {
                        sb.append(Character.toLowerCase(c));
                    }
                    break;
            }
        }

        if (firstCharacterUppercase) {
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        }

        return sb.toString();
    }

}
