package net.anatolich.iris.domain.settlement;

import org.javamoney.moneta.Money;

import java.util.List;

public interface Bank {
    Money getAccountBalance(BankAccount.Id bankAccountId);

    List<BankAccount> getAccounts();
}
