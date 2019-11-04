package net.anatolich.iris.domain.settlement;

import org.javamoney.moneta.Money;

public interface Bank {
    Money getAccountBalance(BankAccountId bankAccountId);
}
