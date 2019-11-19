package net.anatolich.iris.settlement;

import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;

import java.util.Random;
import javax.money.Monetary;
import net.anatolich.iris.domain.settlement.AccountingAccount;
import net.anatolich.iris.domain.settlement.BankAccount;
import net.anatolich.iris.domain.settlement.SettlementCheck;
import net.anatolich.iris.domain.settlement.SettlementProperties;
import net.anatolich.iris.domain.settlement.SettlementService;
import org.assertj.core.api.Assertions;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

@ExtendWith(MockitoExtension.class)
@DisplayName("check settlement on schedule")
class CheckSettlementOnScheduleTest {

    @Mock
    private ApplicationEventPublisher publisher;
    @Captor
    private ArgumentCaptor<SettlementCheck> checkCaptor;

    @DisplayName("for equal balances")
    @Test
    void compareEqualBalances() {
        MockBank bank = new MockBank();
        MockAccountingSystem accounting = new MockAccountingSystem();
        SettlementService settlement = new SettlementService(bank, accounting, new SettlementProperties(), publisher);
        BankAccount.Id bankAccountId = BankAccount.Id.random();
        Money amount = random();
        bank.setAccountBalance(bankAccountId, amount);
        settlement.selectBankAccount(bankAccountId);

        AccountingAccount.Id accountingAccountId = AccountingAccount.Id.random();
        accounting.setBalance(accountingAccountId, amount);
        settlement.selectAccountingAccount(accountingAccountId);

        settlement.checkSettlementOnSchedule();
        verify(publisher, only()).publishEvent(checkCaptor.capture());
        SettlementCheck settlementCheck = checkCaptor.getValue();

        Assertions.assertThat(settlementCheck.isSettled())
                .as("balance comparison for the same amounts must be settled")
                .isTrue();
        Assertions.assertThat(settlementCheck.getBankingBalance())
                .as("incorrect balance of the bank account")
                .isEqualTo(amount);
        Assertions.assertThat(settlementCheck.getAccountingBalance())
                .as("incorrect balance of the accounting accounts")
                .isEqualTo(amount);
    }

    @DisplayName("for unsettled balances")
    @Test
    void compareDifferentBalances() {
        Money bankAmount = random();
        Money accountingAmount = bankAmount.add(random());
        MockBank bank = new MockBank();
        MockAccountingSystem accounting = new MockAccountingSystem();
        SettlementService settlement = new SettlementService(bank, accounting, new SettlementProperties(), publisher);
        BankAccount.Id bankAccountId = BankAccount.Id.random();
        bank.setAccountBalance(bankAccountId, bankAmount);
        settlement.selectBankAccount(bankAccountId);

        AccountingAccount.Id accountingAccountId = AccountingAccount.Id.random();
        accounting.setBalance(accountingAccountId, accountingAmount);
        settlement.selectAccountingAccount(accountingAccountId);

        settlement.checkSettlementOnSchedule();
        verify(publisher, only()).publishEvent(checkCaptor.capture());
        SettlementCheck settlementCheck = checkCaptor.getValue();

        Assertions.assertThat(settlementCheck.isSettled())
                .as("balances not equal")
                .isFalse();
        Assertions.assertThat(settlementCheck.getBankingBalance())
                .as("incorrect balance of the bank account")
                .isEqualTo(bankAmount);
        Assertions.assertThat(settlementCheck.getAccountingBalance())
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
