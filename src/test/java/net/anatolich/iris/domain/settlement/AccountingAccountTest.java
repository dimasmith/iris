package net.anatolich.iris.domain.settlement;

import org.assertj.core.api.Assertions;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;

import java.util.Currency;

class AccountingAccountTest {

    @Test
    void currency() {
        final AccountingAccount account = new AccountingAccount(AccountingAccount.Id.random(), Money.of(100, "UAH"));
        final Currency currency = account.getCurrency();

        Assertions.assertThat(currency)
                .isEqualTo(Currency.getInstance("UAH"));
    }
}
