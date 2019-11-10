package net.anatolich.iris.infra.homemoney;

import net.anatolich.iris.domain.settlement.AccountingAccountId;
import net.anatolich.iris.domain.settlement.AccountingSystem;
import org.javamoney.moneta.Money;

public class Homemoney implements AccountingSystem {
    private final HomemoneyApiClient apiClient;
    private final CurrencyResolver currencyResolver;

    public Homemoney(HomemoneyApiClient apiClient, CurrencyResolver currencyResolver) {
        this.apiClient = apiClient;
        this.currencyResolver = currencyResolver;
    }

    @Override
    public Money getAccountBalance(AccountingAccountId accountingAccountId) {
        return apiClient.getBalanceList()
                .getAccount(accountingAccountId, currencyResolver)
                .getBalance();
    }
}
