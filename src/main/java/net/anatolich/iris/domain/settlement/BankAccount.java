package net.anatolich.iris.domain.settlement;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.javamoney.moneta.Money;

import javax.money.CurrencyUnit;
import java.util.UUID;

@Value
@EqualsAndHashCode(of = "id")
public class BankAccount {
    private final Id id;
    private final Money balance;

    public BankAccount(Id id, Money balance) {
        this.id = id;
        this.balance = balance;
    }

    public CurrencyUnit getCurrency() {
        return getBalance().getCurrency();
    }

    @Value
    public static class Id {
        private final String value;

        public static Id random() {
            return new Id(UUID.randomUUID().toString());
        }
    }
}
