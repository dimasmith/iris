package net.anatolich.iris.infra.demo;

import net.anatolich.iris.domain.settlement.AccountingSystem;
import net.anatolich.iris.domain.settlement.Bank;
import net.anatolich.iris.domain.settlement.SettlementSettingsRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DemoInfraConfiguration {

    @Bean
    public Bank bank() {
        return new DemoBank();
    }

    @Bean
    public AccountingSystem accountingSystem() {
        return new DemoAccountingSystem();
    }

    @Bean
    public SettlementSettingsRepository settlementSettingsRepository(DemoSettlementSettings demoSettlementSettings) {
        return new EnvironmentSettlementSettingsRepository(demoSettlementSettings);
    }
}
