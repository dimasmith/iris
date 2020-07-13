package net.anatolich.iris.subscription.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("subscription entity test")
class SubscriptionTest {

    @Test
    @DisplayName("check invariants on creation")
    void createNewSubscription() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> Subscription.forNewService(null, Money.of(100, "UAH")));

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> Subscription.forNewService(new ServiceProvider("Dropbox", null, null), null));
    }
}
