package net.anatolich.iris.infra.homemoney;

import net.anatolich.iris.domain.settlement.InvalidCurrencyCodeException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.util.Map;
import java.util.stream.Stream;


class CurrencyResolverTest {

    @ParameterizedTest(name = "resolve currency {1} by id {0}")
    @MethodSource("currencies")
    void resolveMappedCurrency(String currencyId, CurrencyUnit expectedCurrency) {
        final HomemoneyProperties properties = new HomemoneyProperties();
        properties.setCurrencies(Map.of("970", "UAH", "980", "USD"));
        final CurrencyResolver currencyResolver = new CurrencyResolver(properties);

        final CurrencyUnit currency = currencyResolver.resolveCurrency(currencyId);

        Assertions.assertThat(currency)
                .isEqualTo(expectedCurrency);
    }

    @Test
    void failOnUnmappedCurrency() {
        final HomemoneyProperties properties = new HomemoneyProperties();
        properties.setCurrencies(Map.of("970", "UAH", "980", "USD"));
        final CurrencyResolver currencyResolver = new CurrencyResolver(properties);

        Assertions.assertThatExceptionOfType(InvalidCurrencyCodeException.class)
                .isThrownBy(() -> currencyResolver.resolveCurrency("990"));
    }

    private static Stream<Arguments> currencies() {
        return Stream.of(
                Arguments.of("970", Monetary.getCurrency("UAH")),
                Arguments.of("980", Monetary.getCurrency("USD"))
        );
    }
}
