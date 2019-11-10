package net.anatolich.iris.domain.settlement;

import lombok.Value;
import org.javamoney.moneta.Money;

@Value
public class AccountingAccount {
    private final AccountingAccountId id;
    private final Money balance;
}
