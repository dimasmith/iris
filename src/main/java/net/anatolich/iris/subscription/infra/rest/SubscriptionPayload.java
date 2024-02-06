package net.anatolich.iris.subscription.infra.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import net.anatolich.iris.subscription.ServiceData;
import net.anatolich.iris.subscription.SubscriptionData;
import org.javamoney.moneta.Money;

@Data
@AllArgsConstructor
@Builder
public class SubscriptionPayload {
    private ServiceDto service;
    private Money rate;

    public static SubscriptionPayload from(SubscriptionData subscription) {
        final ServiceData service = subscription.service();
        return new SubscriptionPayload(
            new ServiceDto(service.name(), service.url(), service.description()),
            subscription.rate()
        );
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class ServiceDto {
        private String name;
        private String url;
        private String description;

        public ServiceData toServiceData() {
            return new ServiceData(name, url, description);
        }
    }
}
