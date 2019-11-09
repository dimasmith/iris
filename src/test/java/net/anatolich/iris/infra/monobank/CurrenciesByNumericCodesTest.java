package net.anatolich.iris.infra.monobank;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.money.CurrencyQueryBuilder;
import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.util.Optional;

class CurrenciesByNumericCodesTest {

    @Test
    void findCurrencyByNumericCode() {
        final int code = 980;
        final CurrencyUnit currency = Monetary.getCurrency(CurrencyQueryBuilder.of().setNumericCodes(code).build());

        final Optional<CurrencyUnit> parsedCurrency = CurrenciesByNumericCodes.findCurrencyByCode(code);

        Assertions.assertThat(parsedCurrency)
                .hasValue(currency);
    }
}