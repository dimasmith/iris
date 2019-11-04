package net.anatolich.iris.settlement;

import net.anatolich.iris.domain.settlement.Bank;
import net.anatolich.iris.domain.settlement.BankAccountId;
import org.javamoney.moneta.Money;

import java.util.HashMap;
import java.util.Map;

class MockBank implements Bank {
    private final Map<BankAccountId, Money> balances = new HashMap<>();

    @Override
    public Money getAccountBalance(BankAccountId bankAccountId) {
        return balances.get(bankAccountId);
    }

    void setAccountBalance(BankAccountId bankAccountId, Money balance) {
        balances.put(bankAccountId, balance);
    }
}
