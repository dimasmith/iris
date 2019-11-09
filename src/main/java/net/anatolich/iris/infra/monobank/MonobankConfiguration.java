package net.anatolich.iris.infra.monobank;

import lombok.extern.java.Log;
import net.anatolich.iris.domain.settlement.Bank;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@ConditionalOnProperty(name = "iris.banking", havingValue = "monobank")
@Log
public class MonobankConfiguration {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }

    @Bean
    public Bank monobank(RestTemplate restTemplate) {
        log.info("using banking: monobank");
        return new Monobank(restTemplate, monobankProperties());
    }

    @Bean
    public MonobankProperties monobankProperties() {
        return new MonobankProperties();
    }
}
