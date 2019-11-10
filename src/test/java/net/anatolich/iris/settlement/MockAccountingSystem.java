package net.anatolich.iris.settlement;

import net.anatolich.iris.domain.settlement.AccountingAccount;
import net.anatolich.iris.domain.settlement.AccountingAccountId;
import net.anatolich.iris.domain.settlement.AccountingSystem;
import org.javamoney.moneta.Money;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class MockAccountingSystem implements AccountingSystem {

    private final Map<AccountingAccountId, Money> balances = new HashMap<>();

    @Override
    public Money getAccountBalance(AccountingAccountId accountingAccountId) {
        return balances.get(accountingAccountId);
    }

    @Override
    public List<AccountingAccount> getAccounts() {
        return List.of();
    }

    void setBalance(AccountingAccountId accountingAccountId, Money balance) {
        balances.put(accountingAccountId, balance);
    }
}
