package net.anatolich.iris.infra.monobank;

import net.anatolich.iris.domain.settlement.InvalidCurrencyCodeException;

import javax.money.*;
import java.util.Optional;

final class CurrenciesByNumericCodes {

    private CurrenciesByNumericCodes() {
        throw new UnsupportedOperationException("utility class must not be instantiated");
    }

    static Optional<CurrencyUnit> findCurrencyByCode(int code) {
        try {
            CurrencyQuery currencyQuery = CurrencyQueryBuilder.of().setNumericCodes(code).build();
            final CurrencyUnit currency = Monetary.getCurrency(currencyQuery);
            return Optional.of(currency);
        } catch (MonetaryException e) {
            return Optional.empty();
        }

    }

    static CurrencyUnit getCurrencyByCode(int currencyCode) {
        return findCurrencyByCode(currencyCode)
                .orElseThrow(() -> new InvalidCurrencyCodeException(currencyCode));
    }
}
