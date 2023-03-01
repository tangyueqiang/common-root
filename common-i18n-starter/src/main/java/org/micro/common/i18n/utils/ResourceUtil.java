package org.micro.common.i18n.utils;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;

public class ResourceUtil {

    private static final ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

    public static Resource[] getResources(String locationPattern) throws IOException {
        return resolver.getResources(locationPattern);
    }

    public static Resource getResource(String location) {
        return resolver.getResource(location);
    }

}
