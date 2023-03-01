package org.micro.common.logging.autoconfig;

import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/***
 * 启用日志组件配置
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({org.micro.common.logging.autoconfig.LoggingAutoConfig.class,
        org.micro.common.logging.autoconfig.ProducerAutoConfig.class,
        ConsumerAutoConfig.class})
public @interface EnableLogging {

}
