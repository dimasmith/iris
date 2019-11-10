package net.anatolich.iris.domain.settlement;

import lombok.Value;
import org.javamoney.moneta.Money;

import java.util.Currency;

@Value
public class AccountingAccount {
    private final AccountingAccountId id;
    private final Money balance;

    public Currency getCurrency() {
        return Currency.getInstance(balance.getCurrency().getCurrencyCode());
    }
}
