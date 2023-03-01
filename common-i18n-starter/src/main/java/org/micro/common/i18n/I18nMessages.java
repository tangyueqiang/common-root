package org.micro.common.i18n;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 注入后即可使用<br>
 * Example:<br>
 * {@link @Autowired}<br>
 * private I18nMessages i18n;
 */
public class I18nMessages {

    private final Map<String, I18nProperties> i18n;

    public I18nMessages(Map<String, I18nProperties> i18n) {
        super();
        this.i18n = i18n == null ? new HashMap<>() : i18n;
    }

    public Map<String, I18nProperties> getI18n() {
        return i18n;
    }

    public I18nProperties getI18n(String language) {
        Locale locale = Locale.CHINA;
        language = "en".equalsIgnoreCase(language) ? "en_US" : language;
        if (StringUtils.contains(language, "_")) {
            String[] names = language.split("_");
            locale = new Locale(names[0], names[1]);
        }
        return getI18n(locale.getLanguage(), locale.getCountry());
    }

    public I18nProperties getI18n(String language, String country) {
        Locale locale = new Locale(language, country);
        return i18n.get(locale.toString());
    }

}
