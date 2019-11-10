package net.anatolich.iris.settlement;

import net.anatolich.iris.domain.settlement.AccountingAccount;
import net.anatolich.iris.domain.settlement.BalanceComparison;
import net.anatolich.iris.domain.settlement.BankAccount;
import net.anatolich.iris.domain.settlement.SettlementService;
import org.assertj.core.api.Assertions;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;

import javax.money.Monetary;
import java.util.Random;


class CompareAccountingAndBankBalancesTest {

    @Test
    void compareEqualBalances() {
        MockBank bank = new MockBank();
        MockAccountingSystem accounting = new MockAccountingSystem();
        SettlementService settlement = new SettlementService(bank, accounting, new MockSettlementSettingsRepository());
        BankAccount.Id bankAccountId = BankAccount.Id.random();
        Money amount = random();
        bank.setAccountBalance(bankAccountId, amount);
        settlement.selectBankAccount(bankAccountId);

        AccountingAccount.Id accountingAccountId = AccountingAccount.Id.random();
        accounting.setBalance(accountingAccountId, amount);
        settlement.selectAccountingAccount(accountingAccountId);

        BalanceComparison balanceComparison = settlement.compareAccountingAndBankBalances();

        Assertions.assertThat(balanceComparison.isSettled())
                .as("balance comparison for the same amounts must be settled")
                .isTrue();
        Assertions.assertThat(balanceComparison.getBankingBalance())
                .as("incorrect balance of the bank account")
                .isEqualTo(amount);
        Assertions.assertThat(balanceComparison.getAccountingBalance())
                .as("incorrect balance of the accounting accounts")
                .isEqualTo(amount);
    }

    @Test
    void compareDifferentBalances() {
        Money bankAmount = random();
        Money accountingAmount = bankAmount.add(random());
        MockBank bank = new MockBank();
        MockAccountingSystem accounting = new MockAccountingSystem();
        SettlementService settlement = new SettlementService(bank, accounting, new MockSettlementSettingsRepository());
        BankAccount.Id bankAccountId = BankAccount.Id.random();
        bank.setAccountBalance(bankAccountId, bankAmount);
        settlement.selectBankAccount(bankAccountId);

        AccountingAccount.Id accountingAccountId = AccountingAccount.Id.random();
        accounting.setBalance(accountingAccountId, accountingAmount);
        settlement.selectAccountingAccount(accountingAccountId);

        BalanceComparison balanceComparison = settlement.compareAccountingAndBankBalances();

        Assertions.assertThat(balanceComparison.isSettled())
                .as("balances not equal")
                .isFalse();
        Assertions.assertThat(balanceComparison.getBankingBalance())
                .as("incorrect balance of the bank account")
                .isEqualTo(bankAmount);
        Assertions.assertThat(balanceComparison.getAccountingBalance())
                .as("incorrect balance of the accounting accounts")
                .isEqualTo(accountingAmount);
    }

    private Money uah(double amount) {
        final Money monetaryAmount = Money.of(amount, "UAH");
        return monetaryAmount.with(Monetary.getDefaultRounding());
    }

    private Money random() {
        return uah(new Random().nextDouble() * 1000 + 1);
    }
}
