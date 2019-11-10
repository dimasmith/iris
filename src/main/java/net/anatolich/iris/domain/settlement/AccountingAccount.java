package net.anatolich.iris.domain.settlement;

import lombok.Value;
import org.javamoney.moneta.Money;

import java.io.Serializable;
import java.util.Currency;
import java.util.UUID;

@Value
public class AccountingAccount {
    private final Id id;
    private final Money balance;

    public Currency getCurrency() {
        return Currency.getInstance(balance.getCurrency().getCurrencyCode());
    }

    @Value
    public static class Id implements Serializable {
        private static final long serialVersionUID = 1L;
        private final String value;

        public static Id random() {
            return new Id(UUID.randomUUID().toString());
        }
    }
}
