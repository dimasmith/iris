package net.anatolich.iris.infra.monobank;

import net.anatolich.iris.domain.settlement.InvalidCurrencyCodeException;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.util.Optional;

class CurrenciesByNumericCodes {

    private CurrenciesByNumericCodes() {
        throw new UnsupportedOperationException("utility class must not be instantiated");
    }

    static Optional<CurrencyUnit> findCurrencyByCode(int code) {
        return Monetary.getCurrencies().stream()
                .filter(currency -> currency.getNumericCode() == code)
                .findFirst();
    }

    static CurrencyUnit getCurrencyByCode(int currencyCode) {
        return findCurrencyByCode(currencyCode)
                .orElseThrow(() -> new InvalidCurrencyCodeException(currencyCode));
    }
}
