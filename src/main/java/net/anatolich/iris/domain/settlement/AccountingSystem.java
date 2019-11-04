package net.anatolich.iris.domain.settlement;

import org.javamoney.moneta.Money;

public interface AccountingSystem {
    Money getAccountBalance(AccountingAccountId accountingAccountId);
}
