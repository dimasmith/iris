package net.anatolich.iris.infra.homemoney;

import net.anatolich.iris.domain.settlement.AccountingAccount;
import net.anatolich.iris.domain.settlement.IncorrectAccountingAccountException;
import org.assertj.core.api.Assertions;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.money.Monetary;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

class BalanceListDtoTest {

    private CurrencyResolver currencyResolver;

    @BeforeEach
    void setUp() {
        currencyResolver = new CurrencyResolver(Map.of("889740", Monetary.getCurrency("UAH")));
    }

    @Test
    void findAndTransformAccount() {
        final AccountingAccount.Id accountId = new AccountingAccount.Id("Qz9Vs02c/889740");
        final AccountingAccount expectedAccount = new AccountingAccount(accountId, Money.of(200.00, "UAH"));
        final BalanceListDto balanceListDto = exampleBalanceList();

        final AccountingAccount account = balanceListDto.getAccount(accountId, currencyResolver);

        Assertions.assertThat(account)
                .isEqualTo(expectedAccount);
    }

    @Test
    void throwExceptionOnIncorrectAccountId() {
        final AccountingAccount.Id accountId = new AccountingAccount.Id("notfound/889740");
        final BalanceListDto balanceListDto = exampleBalanceList();

        Assertions.assertThatExceptionOfType(IncorrectAccountingAccountException.class)
                .isThrownBy(() -> balanceListDto.getAccount(accountId, currencyResolver));
    }

    @Test
    void throwExceptionOnIncorrectCurrencyId() {
        final AccountingAccount.Id accountId = new AccountingAccount.Id("Qz9Vs02c/notfound");
        final BalanceListDto balanceListDto = exampleBalanceList();

        Assertions.assertThatExceptionOfType(IncorrectAccountingAccountException.class)
                .isThrownBy(() -> balanceListDto.getAccount(accountId, currencyResolver));
    }

    @Test
    void rejectUnsupportedAccountIdFormat() {
        final BalanceListDto balanceListDto = exampleBalanceList();
        AccountingAccount.Id accountWithInvalidSeparator = new AccountingAccount.Id("account-id.currency_id");
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .as("correct account id must split account number and currency code via /")
                .isThrownBy(() -> balanceListDto.getAccount(accountWithInvalidSeparator, currencyResolver));

        AccountingAccount.Id malformatedAccount = new AccountingAccount.Id("account-id/currency_id/");
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .as("correct account id must have only one /")
                .isThrownBy(() -> balanceListDto.getAccount(malformatedAccount, currencyResolver));

    }

    private BalanceListDto exampleBalanceList() {
        final BalanceListDto balanceListDto = new BalanceListDto();
        final BalanceListDto.GroupInfo groupA = new BalanceListDto.GroupInfo();
        groupA.setId("WJceDW");
        final BalanceListDto.AccountInfo accountA1 = new BalanceListDto.AccountInfo();
        accountA1.setId("Qz9Vs02c");
        accountA1.setName("Monobank");
        final BalanceListDto.CurrencyInfo accountA1Uah = new BalanceListDto.CurrencyInfo();
        accountA1Uah.setId("889740");
        accountA1Uah.setBalance(BigDecimal.valueOf(200.00));
        balanceListDto.setGroupInfo(List.of(groupA));
        groupA.setAccountInfo(List.of(accountA1));
        accountA1.setCurrencyInfo(List.of(accountA1Uah));
        return balanceListDto;
    }
}
