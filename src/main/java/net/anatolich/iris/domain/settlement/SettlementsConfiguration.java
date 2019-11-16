package net.anatolich.iris.domain.settlement;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SettlementsConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "iris.settlement")
    public SettlementProperties settlementProperties() {
        return new SettlementProperties();
    }
}
