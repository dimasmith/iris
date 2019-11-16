package net.anatolich.iris.infra.demo;

import net.anatolich.iris.domain.settlement.AccountingAccount;
import net.anatolich.iris.domain.settlement.AccountingSystem;
import net.anatolich.iris.domain.settlement.IncorrectAccountingAccountException;
import org.javamoney.moneta.Money;

import java.util.List;

public class DemoAccounting implements AccountingSystem {

    private final List<AccountingAccount> accounts;

    DemoAccounting() {
        accounts = List.of(
                new AccountingAccount(new AccountingAccount.Id("fdbf828b-1f64-4de5-8070-e074c48141f2"), Money.of(1000, "UAH")),
                new AccountingAccount(new AccountingAccount.Id("e0b2e94e-558a-48ae-8f51-a89a7ca322b7"), Money.of(800, "UAH")),
                new AccountingAccount(new AccountingAccount.Id("fe6f6e10-ef51-4f17-8654-72d6b3d7c695"), Money.of(200, "UAH")),
                new AccountingAccount(new AccountingAccount.Id("a21b5bfc-39cc-4ad7-92d3-5e8f72a85c94"), Money.of(0, "UAH"))
        );
    }

    @Override
    public Money getAccountBalance(AccountingAccount.Id accountingAccountId) {
        return accounts.stream()
                .filter(account -> account.getId().equals(accountingAccountId))
                .findFirst()
                .map(AccountingAccount::getBalance)
                .orElseThrow(() -> new IncorrectAccountingAccountException(accountingAccountId));
    }

    @Override
    public List<AccountingAccount> getAccounts() {
        return accounts;
    }

}
