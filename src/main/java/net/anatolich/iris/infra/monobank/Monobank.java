package net.anatolich.iris.infra.monobank;

import lombok.extern.java.Log;
import net.anatolich.iris.domain.settlement.Bank;
import net.anatolich.iris.domain.settlement.BankAccount;
import net.anatolich.iris.domain.settlement.IncorrectBankAccountException;
import org.javamoney.moneta.Money;

import java.util.List;

@Log
public class Monobank implements Bank {

    private final MonobankApiClient monobankApiClient;

    Monobank(MonobankApiClient monobankApiClient) {
        this.monobankApiClient = monobankApiClient;
    }

    @Override
    public Money getAccountBalance(BankAccount.Id bankAccountId) {
        final List<BankAccount> bankAccounts = monobankApiClient.getClientInfo().toBankAccounts();
        return bankAccounts.stream()
                .filter(bankAccount -> bankAccount.getId().equals(bankAccountId))
                .findFirst()
                .map(BankAccount::getBalance)
                .orElseThrow(() -> new IncorrectBankAccountException(bankAccountId));
    }

    @Override
    public List<BankAccount> getAccounts() {
        final ClientInfo clientInfo = monobankApiClient.getClientInfo();
        return clientInfo.toBankAccounts();
    }

}
