package net.anatolich.iris.infra.homemoney;

import lombok.extern.java.Log;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.cache.CacheManager;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.CreatedExpiryPolicy;
import javax.cache.expiry.Duration;

@Configuration
@ConditionalOnProperty(name = "iris.accounting", havingValue = "homemoney")
@Log
public class HomemoneyConfiguration {

    private final RestTemplateBuilder restTemplateBuilder;
    private final CacheManager cacheManager;

    public HomemoneyConfiguration(RestTemplateBuilder restTemplateBuilder, CacheManager cacheManager) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.cacheManager = cacheManager;
    }

    @Bean
    public Homemoney homemoney() {
        return new Homemoney(homemoneyApiClient(), currencyResolver());
    }

    @Bean
    public CurrencyResolver currencyResolver() {
        return new CurrencyResolver(homemoneyProperties());
    }

    @Bean
    public HomemoneyApiClient homemoneyApiClient() {
        return new HomemoneyApiClient(restTemplateBuilder.build(), homemoneyProperties());
    }

    @Bean
    @ConfigurationProperties(prefix = "iris.homemoney")
    public HomemoneyProperties homemoneyProperties() {
        return new HomemoneyProperties();
    }

    @PostConstruct
    public void setCacheExpirationPolicy() {
        MutableConfiguration<?, ?> configuration = new MutableConfiguration<>()
                .setStoreByValue(false)
                .setExpiryPolicyFactory(CreatedExpiryPolicy.factoryOf(Duration.FIVE_MINUTES));
        cacheManager.createCache("homemoney", configuration);
    }
}
