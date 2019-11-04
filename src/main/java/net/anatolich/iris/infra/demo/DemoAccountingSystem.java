package net.anatolich.iris.infra.demo;

import net.anatolich.iris.domain.settlement.AccountingAccountId;
import net.anatolich.iris.domain.settlement.AccountingSystem;
import net.anatolich.iris.domain.settlement.IncorrectAccountingAccountException;
import org.javamoney.moneta.Money;

import java.util.HashMap;
import java.util.Map;

public class DemoAccountingSystem implements AccountingSystem {

    private final Map<AccountingAccountId, Money> balances;

    DemoAccountingSystem() {
        this.balances = new HashMap<>();
        balances.put(new AccountingAccountId("fdbf828b-1f64-4de5-8070-e074c48141f2"), Money.of(1000, "UAH"));
        balances.put(new AccountingAccountId("e0b2e94e-558a-48ae-8f51-a89a7ca322b7"), Money.of(800, "UAH"));
        balances.put(new AccountingAccountId("fe6f6e10-ef51-4f17-8654-72d6b3d7c695"), Money.of(200, "UAH"));
        balances.put(new AccountingAccountId("a21b5bfc-39cc-4ad7-92d3-5e8f72a85c94"), Money.of(0, "UAH"));
    }

    @Override
    public Money getAccountBalance(AccountingAccountId accountingAccountId) {
        if (!balances.containsKey(accountingAccountId)) {
            throw new IncorrectAccountingAccountException(accountingAccountId);
        }
        return balances.get(accountingAccountId);
    }

}
