package net.anatolich.iris.infra.homemoney;

import net.anatolich.iris.domain.settlement.AccountingAccount;
import net.anatolich.iris.domain.settlement.AccountingAccountId;
import org.assertj.core.api.Assertions;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.money.Monetary;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

@ExtendWith(MockitoExtension.class)
class HomemoneyTest {

    private static final String UAH_ID = "970";
    private static final String USD_ID = "980";
    @Mock
    private HomemoneyApiClient apiClient;
    private CurrencyResolver currencyResolver;
    private Homemoney homemoney;

    @BeforeEach
    void setUp() {
        currencyResolver = new CurrencyResolver(Map.of(
                UAH_ID, Monetary.getCurrency("UAH"),
                USD_ID, Monetary.getCurrency("USD")));
        homemoney = new Homemoney(apiClient, currencyResolver);
    }

    @Test
    void listBalancesFromClient() {
        BalanceListDto balanceList = BalanceListDto.builder()
                .groupInfo(List.of(BalanceListDto.GroupInfo.builder()
                        .id(UUID.randomUUID().toString())
                        .accountInfo(List.of(
                                accountInfo(a -> a
                                        .id("savings-account")
                                        .currencyInfo(List.of(
                                                currencyInfo(UAH_ID, 100),
                                                currencyInfo(USD_ID, 200)
                                        ))),
                                accountInfo(a -> a
                                        .id("deposit-account")
                                        .currencyInfo(List.of(currencyInfo(UAH_ID, 1000)))
                                )
                        )).build()))
                .build();
        Mockito.when(apiClient.getBalanceList()).thenReturn(balanceList);

        final List<AccountingAccount> accounts = homemoney.getAccounts();

        Assertions.assertThat(accounts)
                .containsExactlyInAnyOrder(
                        new AccountingAccount(new AccountingAccountId("savings-account/970"), Money.of(100, "UAH")),
                        new AccountingAccount(new AccountingAccountId("savings-account/980"), Money.of(200, "USD")),
                        new AccountingAccount(new AccountingAccountId("deposit-account/970"), Money.of(1000, "UAH"))
                );
    }

    @Test
    void getAccountBalance() {
        BalanceListDto balanceList = BalanceListDto.builder()
                .groupInfo(List.of(BalanceListDto.GroupInfo.builder()
                        .id(UUID.randomUUID().toString())
                        .accountInfo(List.of(
                                accountInfo(a -> a
                                        .id("savings-account")
                                        .currencyInfo(List.of(
                                                currencyInfo(UAH_ID, 100),
                                                currencyInfo(USD_ID, 200)
                                        ))),
                                accountInfo(a -> a
                                        .id("deposit-account")
                                        .currencyInfo(List.of(currencyInfo(UAH_ID, 1000)))
                                )
                        )).build()))
                .build();
        Mockito.when(apiClient.getBalanceList()).thenReturn(balanceList);

        final Money balance = homemoney.getAccountBalance(new AccountingAccountId("savings-account/970"));

        Assertions.assertThat(balance)
                .isEqualTo(Money.of(100, "UAH"));
    }

    private BalanceListDto.CurrencyInfo currencyInfo(String id, long balance) {
        return currencyInfo(info -> info.id(id).balance(BigDecimal.valueOf(balance)));
    }

    private BalanceListDto.CurrencyInfo currencyInfo(Consumer<BalanceListDto.CurrencyInfo.CurrencyInfoBuilder> build) {
        final BalanceListDto.CurrencyInfo.CurrencyInfoBuilder builder = BalanceListDto.CurrencyInfo.builder();
        build.accept(builder);
        return builder.build();
    }

    private BalanceListDto.AccountInfo accountInfo(Consumer<BalanceListDto.AccountInfo.AccountInfoBuilder> build) {
        final BalanceListDto.AccountInfo.AccountInfoBuilder builder = BalanceListDto.AccountInfo.builder();
        build.accept(builder);
        return builder.build();
    }
}