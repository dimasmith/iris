package net.anatolich.iris.infra.monobank;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.util.Optional;
import java.util.stream.Stream;

class CurrenciesByNumericCodesTest {

    @DisplayName("resolve currencies by codes")
    @ParameterizedTest(name = "currency with code {0} is {1}")
    @MethodSource("currencyCodes")
    void resolveCurrencyByCode(int code, CurrencyUnit currency) {
        final Optional<CurrencyUnit> foundCurrency = CurrenciesByNumericCodes.findCurrencyByCode(code);

        Assertions.assertThat(foundCurrency)
                .hasValue(currency);
    }

    private static Stream<Arguments> currencyCodes() {
        return Stream.of(
                Arguments.of(980, Monetary.getCurrency("UAH")),
                Arguments.of(840, Monetary.getCurrency("USD")),
                Arguments.of(978, Monetary.getCurrency("EUR"))
        );
    }
}