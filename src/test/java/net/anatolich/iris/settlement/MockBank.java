package net.anatolich.iris.settlement;

import net.anatolich.iris.domain.settlement.Bank;
import net.anatolich.iris.domain.settlement.BankAccount;
import org.javamoney.moneta.Money;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class MockBank implements Bank {
    private final Map<BankAccount.Id, Money> balances = new HashMap<>();

    @Override
    public Money getAccountBalance(BankAccount.Id bankAccountId) {
        return balances.get(bankAccountId);
    }

    @Override
    public List<BankAccount> getAccounts() {
        return Collections.emptyList();
    }

    void setAccountBalance(BankAccount.Id bankAccountId, Money balance) {
        balances.put(bankAccountId, balance);
    }
}
