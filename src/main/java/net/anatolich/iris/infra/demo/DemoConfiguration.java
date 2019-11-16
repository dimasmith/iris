package net.anatolich.iris.infra.demo;

import lombok.extern.java.Log;
import net.anatolich.iris.domain.settlement.AccountingSystem;
import net.anatolich.iris.domain.settlement.Bank;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Log
@Configuration
public class DemoConfiguration {

    @Bean
    @ConditionalOnProperty(name = "iris.banking", havingValue = "demo", matchIfMissing = true)
    public Bank bank() {
        log.info("using banking: demo");
        return new DemoBank();
    }

    @Bean
    @ConditionalOnProperty(name = "iris.accounting", havingValue = "demo", matchIfMissing = true)
    public AccountingSystem accountingSystem() {
        log.info("using accounting: demo");
        return new DemoAccounting();
    }

}
