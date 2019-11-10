package net.anatolich.iris.infra.homemoney;

import lombok.extern.slf4j.Slf4j;
import net.anatolich.iris.domain.settlement.InvalidCurrencyCodeException;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
class CurrencyResolver {
    private final Map<String, CurrencyUnit> currenciesByCodes;

    CurrencyResolver(HomemoneyProperties properties) {
        final HashMap<String, CurrencyUnit> currencyByCode = new HashMap<>();
        properties.getCurrencies().forEach((key, value) -> currencyByCode.put(key, Monetary.getCurrency(value)));
        this.currenciesByCodes = currencyByCode;
        currencyByCode.forEach((code, currency) ->
                log.info("[homemoney] registering currency id {} as {}", code, currency));

    }

    CurrencyResolver(Map<String, CurrencyUnit> currenciesByCodes) {
        this.currenciesByCodes = currenciesByCodes;
    }

    CurrencyUnit resolveCurrency(String homemoneyCurrencyId) {
        return Optional.ofNullable(currenciesByCodes.get(homemoneyCurrencyId))
                .orElseThrow(() -> new InvalidCurrencyCodeException(homemoneyCurrencyId));
    }

}
