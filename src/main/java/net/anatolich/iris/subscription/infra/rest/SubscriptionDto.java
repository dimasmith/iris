package net.anatolich.iris.subscription.infra.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import net.anatolich.iris.subscription.domain.ServiceProvider;
import net.anatolich.iris.subscription.domain.Subscription;
import org.javamoney.moneta.Money;

@Data
@AllArgsConstructor
@Builder
public class SubscriptionDto {
    private ServiceDto service;
    private Money rate;

    public static SubscriptionDto from(Subscription subscription) {
        final ServiceProvider serviceProvider = subscription.getServiceProvider();
        return new SubscriptionDto(
            new ServiceDto(serviceProvider.getName(), serviceProvider.getUrl(), serviceProvider.getDescription()),
            subscription.getRate()
        );
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class ServiceDto {
        private String name;
        private String url;
        private String description;

        public ServiceProvider toServiceProvider() {
            return new ServiceProvider(name, url, description);
        }
    }
}
