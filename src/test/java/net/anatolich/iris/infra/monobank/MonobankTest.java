package net.anatolich.iris.infra.monobank;

import net.anatolich.iris.domain.settlement.BankAccount;
import net.anatolich.iris.domain.settlement.IncorrectBankAccountException;
import org.assertj.core.api.Assertions;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class MonobankTest {

    @Mock
    private MonobankApiClient apiClient;
    private Monobank monobank;

    @BeforeEach
    void setUp() {
        monobank = new Monobank(apiClient);
    }

    @Test
    void getAccounts() {
        final ClientInfo.Account uahAccount = ClientInfo.Account.builder()
                .id(UUID.randomUUID().toString())
                .balance(1200)
                .currencyCode(980)
                .build();
        final ClientInfo.Account usdAccount = ClientInfo.Account.builder()
                .id(UUID.randomUUID().toString())
                .balance(4200)
                .currencyCode(840)
                .build();
        final ClientInfo clientInfo = ClientInfo.builder()
                .name("Joe")
                .accounts(List.of(uahAccount, usdAccount))
                .build();
        Mockito.when(apiClient.getClientInfo()).thenReturn(clientInfo);

        final List<BankAccount> bankAccounts = monobank.getAccounts();

        Assertions.assertThat(bankAccounts)
                .containsExactlyInAnyOrder(
                        new BankAccount(new BankAccount.Id(uahAccount.getId()), Money.of(12.00, "UAH")),
                        new BankAccount(new BankAccount.Id(usdAccount.getId()), Money.of(42.00, "USD"))
                );
    }

    @Test
    void getBalanceOfExistingAccount() {
        final ClientInfo.Account uahAccount = ClientInfo.Account.builder()
                .id(UUID.randomUUID().toString())
                .balance(1200)
                .currencyCode(980)
                .build();
        final ClientInfo.Account usdAccount = ClientInfo.Account.builder()
                .id(UUID.randomUUID().toString())
                .balance(4200)
                .currencyCode(840)
                .build();
        final ClientInfo clientInfo = ClientInfo.builder()
                .name("Joe")
                .accounts(List.of(uahAccount, usdAccount))
                .build();
        Mockito.when(apiClient.getClientInfo()).thenReturn(clientInfo);

        final Money bankAccounts = monobank.getAccountBalance(new BankAccount.Id(uahAccount.getId()));

        Assertions.assertThat(bankAccounts)
                .isEqualTo(Money.of(12.00, "UAH"));
    }

    @Test
    void exceptionWhenGettingBalanceOfMissingAccount() {
        final ClientInfo.Account uahAccount = ClientInfo.Account.builder()
                .id(UUID.randomUUID().toString())
                .balance(1200)
                .currencyCode(980)
                .build();
        final ClientInfo.Account usdAccount = ClientInfo.Account.builder()
                .id(UUID.randomUUID().toString())
                .balance(4200)
                .currencyCode(840)
                .build();
        final ClientInfo clientInfo = ClientInfo.builder()
                .name("Joe")
                .accounts(List.of(uahAccount, usdAccount))
                .build();
        Mockito.when(apiClient.getClientInfo()).thenReturn(clientInfo);

        Assertions.assertThatExceptionOfType(IncorrectBankAccountException.class)
                .isThrownBy(() -> monobank.getAccountBalance(BankAccount.Id.random()));
    }

}