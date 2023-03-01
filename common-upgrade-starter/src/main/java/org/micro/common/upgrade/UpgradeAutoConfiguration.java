package org.micro.common.upgrade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.annotation.Order;

import javax.sql.DataSource;

@Order(PriorityOrdered.HIGHEST_PRECEDENCE)
@Configuration
@EnableConfigurationProperties(UpgradeProperties.class)
@ConditionalOnProperty(prefix = UpgradeProperties.PREFIX, value = "enabled", havingValue = "true")
public class UpgradeAutoConfiguration implements ApplicationListener<ContextRefreshedEvent> {

    private boolean upgraded;

    private SQLUpgrade upgrade;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UpgradeProperties upgradeProperties;

    @Bean
    public SQLUpgrade edpUpgrade() {
        upgrade = upgrade == null ? new SQLUpgrade(upgradeProperties) : upgrade;
        return upgrade;
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (upgraded) {
            return;
        }

        upgrade = upgrade == null ? new SQLUpgrade(upgradeProperties) : upgrade;
        upgrade.upgradeDb(dataSource);
        upgraded = true;
    }

    public UpgradeProperties getUpgradeProperties() {
        return upgradeProperties;
    }

    public SQLUpgrade getUpgrade() {
        return upgrade;
    }

}
