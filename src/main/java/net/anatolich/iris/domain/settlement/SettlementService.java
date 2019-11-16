package net.anatolich.iris.domain.settlement;

import lombok.extern.slf4j.Slf4j;
import org.javamoney.moneta.Money;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class SettlementService {
    private final Bank bank;
    private final AccountingSystem accounting;
    private final SettlementProperties properties;
    private final ApplicationEventPublisher publisher;

    public SettlementService(Bank bank, AccountingSystem accounting, SettlementProperties properties,
        ApplicationEventPublisher publisher) {
        this.bank = bank;
        this.accounting = accounting;
        this.properties = properties;
        this.publisher = publisher;
    }

    public void selectBankAccount(BankAccount.Id bankAccountId) {
        properties.setBankAccount(bankAccountId);
    }

    public void selectAccountingAccount(AccountingAccount.Id accountingAccountId) {
        properties.setAccountingAccount(accountingAccountId);
    }

    public List<BankAccount> listAvailableBankAccounts() {
        return bank.getAccounts();
    }

    public List<AccountingAccount> listAvailableAccountingAccounts() {
        return accounting.getAccounts();
    }

    public SettlementCheck compareAccountingAndBankBalances() {
        final Money bankBalance = bank.getAccountBalance(properties.bankingAccountId());
        final Money accountingBalance = accounting.getAccountBalance(properties.accountingAccountId());
        final SettlementCheck settlementCheck = new SettlementCheck(bankBalance, accountingBalance);
        publisher.publishEvent(settlementCheck);
        return settlementCheck;
    }
}
