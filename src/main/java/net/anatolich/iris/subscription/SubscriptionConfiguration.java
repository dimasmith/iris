package net.anatolich.iris.subscription;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.anatolich.iris.subscription.app.DefaultSubscriptionsService;
import net.anatolich.iris.subscription.domain.SubscriptionRepository;

@Configuration
class SubscriptionConfiguration {
    
    @Bean
    SubscriptionsService subscriptionsService(SubscriptionRepository repository) {
        return new DefaultSubscriptionsService(repository);
    }
}
