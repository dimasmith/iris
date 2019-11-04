package net.anatolich.iris.infra.demo;

import net.anatolich.iris.domain.settlement.Bank;
import net.anatolich.iris.domain.settlement.BankAccountId;
import net.anatolich.iris.domain.settlement.IncorrectBankAccountException;
import org.javamoney.moneta.Money;

import java.util.HashMap;
import java.util.Map;

public class DemoBank implements Bank {

    private Map<BankAccountId, Money> balances;

    DemoBank() {
        this.balances = new HashMap<>();
        balances.put(new BankAccountId("35f2348a-b1a2-41ff-83db-66f7770532ca"), Money.of(1000, "UAH"));
        balances.put(new BankAccountId("bc093edb-9e00-41a4-be31-7b6db4fdcb00"), Money.of(800, "UAH"));
        balances.put(new BankAccountId("f98b60ca-2abd-4457-87d2-fa7500ef7c81"), Money.of(200, "UAH"));
        balances.put(new BankAccountId("1faa53bc-ce0b-4431-a4d0-4c09c867db59"), Money.of(0, "UAH"));
    }

    @Override
    public Money getAccountBalance(BankAccountId bankAccountId) {
        if (!balances.containsKey(bankAccountId)) {
            throw new IncorrectBankAccountException(bankAccountId);
        }
        return balances.get(bankAccountId);
    }

}
