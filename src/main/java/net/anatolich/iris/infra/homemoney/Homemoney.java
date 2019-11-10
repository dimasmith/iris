package net.anatolich.iris.infra.homemoney;

import net.anatolich.iris.domain.settlement.AccountingAccount;
import net.anatolich.iris.domain.settlement.AccountingSystem;
import org.javamoney.moneta.Money;

import java.util.List;

public class Homemoney implements AccountingSystem {
    private final HomemoneyApiClient apiClient;
    private final CurrencyResolver currencyResolver;

    public Homemoney(HomemoneyApiClient apiClient, CurrencyResolver currencyResolver) {
        this.apiClient = apiClient;
        this.currencyResolver = currencyResolver;
    }

    @Override
    public Money getAccountBalance(AccountingAccount.Id accountingAccountId) {
        return apiClient.getBalanceList()
                .getAccount(accountingAccountId, currencyResolver)
                .getBalance();
    }

    @Override
    public List<AccountingAccount> getAccounts() {
        return apiClient.getBalanceList().toAccounts(currencyResolver);
    }
}
