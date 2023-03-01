package org.micro.commons.basic.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class URLUtil {

    private static final Logger logger = LoggerFactory.getLogger(URLUtil.class);

    private static final String RE_TOP_DOMAIN = "(com\\.cn|net\\.cn|gov\\.cn|org\\.nz|org\\.cn|com|net|org|gov)";

    // 一级域名提取
    private static final String RE_TOP_1 = "(\\w*\\.?)\\." + RE_TOP_DOMAIN;
    private static final Pattern PATTEN_TOP1 = Pattern.compile(RE_TOP_1);
    // 二级域名提取
    private static final String RE_TOP_2 = "(\\w*\\.?){2}\\." + RE_TOP_DOMAIN;
    private static final Pattern PATTEN_TOP2 = Pattern.compile(RE_TOP_2);
    // 三级域名提取
    private static final String RE_TOP_3 = "(\\w*\\.?){3}\\." + RE_TOP_DOMAIN;
    private static final Pattern PATTEN_TOP3 = Pattern.compile(RE_TOP_3);
    private static final Pattern PATTEN_IP = Pattern.compile("((http://)|(https://))?((\\d+\\.){3}(\\d+))");

    public static String getDomain(String url, int level) {
        try {
            URL u = new URL(url);
            if ("localhost".equalsIgnoreCase(u.getHost())) {
                return u.getHost();
            }
        } catch (Exception e) {
            logger.error(url, e);
        }

        Matcher matcher = PATTEN_IP.matcher(url);
        if (matcher.find()) {
            return matcher.group(4);
        }

        switch (level) {
            case 1:
                matcher = PATTEN_TOP1.matcher(url);
                break;
            case 2:
                matcher = PATTEN_TOP2.matcher(url);
                break;
            case 3:
                matcher = PATTEN_TOP3.matcher(url);
                break;
            default:
                return "";
        }

        if (matcher.find()) {
            return matcher.group(0);
        }

        return "";
    }

    /**
     * 获取参数对
     *
     * @param query 参数
     * @return list
     */
    public static List<String> getParamPairs(String query) {
        List<String> params = new ArrayList<>();
        try {
            query = query.trim();
            if (StringUtils.isNotBlank(query)) {
                String[] arr = query.split("&");
                params.addAll(Arrays.asList(arr));
            }
        } catch (Exception e) {
            logger.error(query, e);
        }

        return params;
    }

    /**
     * 获取host前缀
     *
     * @param url 路径
     * @return 前缀
     */
    public static String getHostPrefix(String url) {
        String search = ".";
        try {
            URL u = new URL(url);
            String host = u.getHost();
            if (!host.contains(search)) {
                return host;
            }

            try {
                Long.parseLong(host.replace(search, ""));
                return host;
            } catch (Exception ignored) {
            }

            return host.split("\\.")[0];
        } catch (Exception e) {
            logger.error(url, e);
        }

        return null;
    }
}
