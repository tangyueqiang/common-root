package org.micro.common.logging.autoconfig;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextProvider implements ApplicationContextAware {

    private static ApplicationContext context;

    public static ApplicationContext getApplicationContext() {
        return context;
    }

    @Override
    public void setApplicationContext(ApplicationContext ac)
            throws BeansException {
        context = ac;

    }

    public static <T> T getBean(Class<T> tClass) {
        if (context == null) {
            return null;
        }
        return context.getBean(tClass);
    }

    public static <T> T getBean(String name, Class<T> tClass) {
        if (context == null) {
            return null;
        }
        return context.getBean(name, tClass);
    }
}
