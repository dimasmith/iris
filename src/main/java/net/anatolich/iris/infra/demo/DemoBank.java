package net.anatolich.iris.infra.demo;

import net.anatolich.iris.domain.settlement.Bank;
import net.anatolich.iris.domain.settlement.BankAccount;
import net.anatolich.iris.domain.settlement.IncorrectBankAccountException;
import org.javamoney.moneta.Money;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DemoBank implements Bank {

    private final Map<BankAccount.Id, BankAccount> accountsById;
    private final List<BankAccount> bankAccounts;

    DemoBank() {
        this.bankAccounts = List.of(
                new BankAccount(
                        new BankAccount.Id("35f2348a-b1a2-41ff-83db-66f7770532ca"),
                        Money.of(1000, "UAH")),
                new BankAccount(
                        new BankAccount.Id("bc093edb-9e00-41a4-be31-7b6db4fdcb00"),
                        Money.of(800, "UAH")),
                new BankAccount(
                        new BankAccount.Id("f98b60ca-2abd-4457-87d2-fa7500ef7c81"),
                        Money.of(200, "UAH")),
                new BankAccount(
                        new BankAccount.Id("1faa53bc-ce0b-4431-a4d0-4c09c867db59"),
                        Money.of(0, "UAH"))
        );
        accountsById = bankAccounts.stream()
                .collect(Collectors.toMap(BankAccount::getId, Function.identity()));
    }

    @Override
    public Money getAccountBalance(BankAccount.Id bankAccountId) {
        if (!accountsById.containsKey(bankAccountId)) {
            throw new IncorrectBankAccountException(bankAccountId);
        }
        return accountsById.get(bankAccountId).getBalance();
    }

    @Override
    public List<BankAccount> getAccounts() {
        return Collections.unmodifiableList(bankAccounts);
    }

}
