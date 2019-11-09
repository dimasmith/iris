package net.anatolich.iris.infra.monobank;

import lombok.Data;
import net.anatolich.iris.domain.settlement.BankAccount;
import org.javamoney.moneta.Money;

import javax.money.CurrencyUnit;
import java.util.List;
import java.util.stream.Collectors;

@Data
class ClientInfo {
    private String name;
    private List<Account> accounts;

    List<BankAccount> toBankAccounts() {
        return getAccounts().stream()
                .map(Account::toBankAccount)
                .collect(Collectors.toList());
    }

    @Data
    static class Account {
        private String id;
        private int currencyCode;
        private String cashbackType;
        private long balance;
        private long creditLimit;

        BankAccount toBankAccount() {
            final BankAccount.Id accountId = new BankAccount.Id(id);
            final CurrencyUnit currency = CurrenciesByNumericCodes.getCurrencyByCode(currencyCode);
            final Money openingBalance = Money.of(balance, currency).divide(100);
            return new BankAccount(accountId, openingBalance);
        }

    }
}
