package net.anatolich.iris.subscription.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("service provider value")
class ServiceProviderTest {

    @Test
    @DisplayName("check invariants on creation")
    void checkCreationInvariants() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
            .as("name must not be null")
            .isThrownBy(() -> new ServiceProvider(null, "", ""));

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
            .as("name must not be empty")
            .isThrownBy(() -> new ServiceProvider("", "", ""));

        Assertions.assertThatCode(() -> new ServiceProvider("Dropbox", null, null))
            .doesNotThrowAnyException();
    }
}
