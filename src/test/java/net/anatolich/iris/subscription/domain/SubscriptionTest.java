package net.anatolich.iris.subscription.domain;

import org.assertj.core.api.Assertions;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import net.anatolich.iris.subscription.ServiceData;

@DisplayName("subscription entity test")
class SubscriptionTest {

    @Test
    @DisplayName("check invariants on creation")
    void createNewSubscription() {
        final Money rate = Money.of(100, "UAH");
        final ServiceData serviceProvider = new ServiceData("Dropbox", null, null);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> Subscription.forNewService(null, rate));

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> Subscription.forNewService(serviceProvider, null));
    }
}
