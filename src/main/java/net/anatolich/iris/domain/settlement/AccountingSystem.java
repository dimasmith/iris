package net.anatolich.iris.domain.settlement;

import org.javamoney.moneta.Money;

import java.util.List;

public interface AccountingSystem {
    Money getAccountBalance(AccountingAccountId accountingAccountId);

    List<AccountingAccount> getAccounts();
}
