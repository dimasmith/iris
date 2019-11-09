package net.anatolich.iris.infra.monobank;

import lombok.Data;
import net.anatolich.iris.domain.settlement.BankAccount;
import net.anatolich.iris.domain.settlement.InvalidCurrencyCodeException;
import org.javamoney.moneta.Money;

import javax.money.CurrencyUnit;
import java.util.List;

@Data
class ClientInfo {
    private String name;
    private List<Account> accounts;

    @Data
    static class Account {
        private String id;
        private int currencyCode;
        private String cashbackType;
        private long balance;
        private long creditLimit;

        BankAccount toBankAccount() {
            BankAccount.Id accountId = new BankAccount.Id(id);
            CurrencyUnit currency = CurrenciesByNumericCodes.findCurrencyByCode(currencyCode)
                    .orElseThrow(() -> new InvalidCurrencyCodeException(currencyCode));
            Money openingBalance = Money.of(balance, currency);
            return new BankAccount(accountId, openingBalance);
        }
    }
}
