package org.micro.commons.springfox.autoconfig;

import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***
 * 启用Springfox Swagger API
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(org.micro.commons.springfox.autoconfig.SpringfoxAutoConfiguration.class)
@Documented
@Inherited
public @interface EnableSpringfox {

}
