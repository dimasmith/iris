package net.anatolich.iris.infra.monobank;

import net.anatolich.iris.domain.settlement.InvalidCurrencyCodeException;

import javax.money.CurrencyQueryBuilder;
import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.util.Optional;

class CurrenciesByNumericCodes {

    private CurrenciesByNumericCodes() {
        throw new UnsupportedOperationException("utility class must not be instantiated");
    }

    static Optional<CurrencyUnit> findCurrencyByCode(int code) {
        final CurrencyUnit currency = Monetary.getCurrency(CurrencyQueryBuilder.of().setNumericCodes(code).build());
        return Optional.ofNullable(currency);
    }

    static CurrencyUnit getCurrencyByCode(int currencyCode) {
        return findCurrencyByCode(currencyCode)
                .orElseThrow(() -> new InvalidCurrencyCodeException(currencyCode));
    }
}
