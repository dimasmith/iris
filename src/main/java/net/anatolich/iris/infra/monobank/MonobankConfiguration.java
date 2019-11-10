package net.anatolich.iris.infra.monobank;

import lombok.extern.java.Log;
import net.anatolich.iris.domain.settlement.Bank;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.cache.CacheManager;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;

@Configuration
@ConditionalOnProperty(name = "iris.banking", havingValue = "monobank")
@Log
public class MonobankConfiguration {

    private final RestTemplateBuilder restTemplateBuilder;
    private final CacheManager cacheManager;

    public MonobankConfiguration(RestTemplateBuilder restTemplateBuilder, CacheManager cacheManager) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.cacheManager = cacheManager;
    }

    @Bean
    public RestTemplate restTemplate() {
        return restTemplateBuilder.build();
    }

    @Bean
    public Bank monobank() {
        log.info("using banking: monobank");
        return new Monobank(monobankApiClient());
    }

    @Bean
    @ConfigurationProperties(prefix = "iris.monobank")
    public MonobankProperties monobankProperties() {
        return new MonobankProperties();
    }

    @Bean
    public MonobankApiClient monobankApiClient() {
        return new MonobankApiClient(restTemplate(), monobankProperties());
    }

    @PostConstruct
    public void setCacheExpirationPolicy() {
        MutableConfiguration<?, ?> configuration = new MutableConfiguration<>()
                .setStoreByValue(false)
                .setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(Duration.FIVE_MINUTES));
        cacheManager.createCache("monobank", configuration);
    }
}
