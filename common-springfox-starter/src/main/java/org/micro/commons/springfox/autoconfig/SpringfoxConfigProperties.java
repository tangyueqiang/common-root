package org.micro.commons.springfox.autoconfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(SpringfoxConfigProperties.PREFIX)
public class SpringfoxConfigProperties {

    public static final String PREFIX = "springfox.documentation";

    @Value("${spring.application.name}")
    private String application;

    /**
     * API Controller包
     */
    private String baseApisPackage = "com";

    public String getApplication() {
        return application;
    }

    public String getBaseApisPackage() {
        return baseApisPackage;
    }

    public void setBaseApisPackage(String baseApisPackage) {
        this.baseApisPackage = baseApisPackage;
    }

}
