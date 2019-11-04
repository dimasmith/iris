package net.anatolich.iris.domain.settlement;

import lombok.extern.slf4j.Slf4j;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SettlementService {
    private final Bank bank;
    private final AccountingSystem accounting;
    private final SettlementSettingsRepository settingsRepository;

    public SettlementService(Bank bank, AccountingSystem accounting, SettlementSettingsRepository settingsRepository) {
        this.bank = bank;
        this.accounting = accounting;
        this.settingsRepository = settingsRepository;
    }

    public void selectBankAccount(BankAccountId bankAccountId) {
        final SettlementSettings settlementSettings = settingsRepository.getSettings();
        settlementSettings.setBankAccountId(bankAccountId);
        settingsRepository.save(settlementSettings);
    }

    public void selectAccountingAccount(AccountingAccountId accountingAccountId) {
        final SettlementSettings settlementSettings = settingsRepository.getSettings();
        settlementSettings.setAccountingAccountId(accountingAccountId);
        settingsRepository.save(settlementSettings);
    }

    public BalanceComparison compareAccountingAndBankBalances() {
        final SettlementSettings settings = settingsRepository.getSettings();
        log.info("checking settlement of {}", settings);
        final Money bankBalance = bank.getAccountBalance(settings.getBankAccountId());
        final Money accountingBalance = accounting.getAccountBalance(settings.getAccountingAccountId());
        return new BalanceComparison(bankBalance, accountingBalance);
    }

}
