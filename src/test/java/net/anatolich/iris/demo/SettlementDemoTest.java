package net.anatolich.iris.demo;

import net.anatolich.iris.domain.settlement.AccountingAccount;
import net.anatolich.iris.domain.settlement.SettlementCheck;
import net.anatolich.iris.domain.settlement.BankAccount;
import net.anatolich.iris.domain.settlement.SettlementService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Disabled("waiting for testcontainers")
class SettlementDemoTest {

    @Autowired
    private SettlementService settlements;

    @Test
    void configureAccountsAndCheckSettlement() {
        final AccountingAccount accountingAccount = settlements.listAvailableAccountingAccounts().get(0);
        settlements.selectAccountingAccount(accountingAccount.getId());

        final BankAccount bankAccount = settlements.listAvailableBankAccounts().get(0);
        settlements.selectBankAccount(bankAccount.getId());

        final SettlementCheck comparison = settlements.compareAccountingAndBankBalances();

        Assertions.assertThat(comparison.getAccountingBalance())
                .isEqualTo(accountingAccount.getBalance());

        Assertions.assertThat(comparison.getBankingBalance())
                .isEqualTo(bankAccount.getBalance());
    }
}

