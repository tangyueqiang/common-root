package org.micro.common.i18n;

import org.apache.commons.lang.StringUtils;
import org.micro.commons.basic.beans.Result;
import org.micro.commons.basic.exception.BasicException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.Locale;
import java.util.MissingFormatArgumentException;

public abstract class I18nResponseAdvice implements ResponseBodyAdvice<Object> {

    @Autowired
    private I18nMessages i18n;

    @Autowired
    private I18nConfigProperties config;

    @Override
    public boolean supports(MethodParameter returnType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
                                  ServerHttpResponse response) {
        if (!(body instanceof Result)) {
            return body;
        }

        Result res = (Result) body;
        if (res.getModel() instanceof I18nModel) {
            I18nModel model = (I18nModel) res.getModel();
            String lang = request.getHeaders().getFirst(config.getHeaderKey());
            lang = StringUtils.isBlank(lang) ? Locale.CHINA.toString() : lang;

            if (i18n.getI18n(lang) != null) {
                try {
                    String msg = i18n.getI18n(lang).getProperty(model.getKey(), model.getArgs());
                    res.setMsg(msg);
                } catch (MissingFormatArgumentException e) {
                    model.setDetail(i18n.getI18n(lang).getProperty(model.getKey()));
                    res.setMsg("i18n format error: " + BasicException.getRootCauseMsg(e));
                }
            }
        }

        return res;
    }

}
