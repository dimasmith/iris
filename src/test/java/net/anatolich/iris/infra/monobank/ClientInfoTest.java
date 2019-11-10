package net.anatolich.iris.infra.monobank;

import net.anatolich.iris.domain.settlement.BankAccount;
import org.assertj.core.api.Assertions;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;

import javax.money.Monetary;
import java.util.List;
import java.util.UUID;

class ClientInfoTest {

    @Test
    void convertToBankAccounts() {
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

        final List<BankAccount> bankAccounts = clientInfo.toBankAccounts();

        Assertions.assertThat(bankAccounts)
                .containsExactlyInAnyOrder(
                        new BankAccount(new BankAccount.Id(uahAccount.getId()), Money.of(12.00, "UAH")),
                        new BankAccount(new BankAccount.Id(usdAccount.getId()), Money.of(42.00, "USD"))
                );
    }

    @Test
    void convertToBankAccount() {
        final ClientInfo.Account accountDto = ClientInfo.Account.builder()
                .balance(1200)
                .currencyCode(980)
                .id(UUID.randomUUID().toString())
                .creditLimit(100)
                .cashbackType("UAH")
                .build();

        final BankAccount bankAccount = accountDto.toBankAccount();

        Assertions.assertThat(bankAccount.getId())
                .isEqualTo(new BankAccount.Id(accountDto.getId()));

        Assertions.assertThat(bankAccount.getCurrency())
                .isEqualTo(Monetary.getCurrency("UAH"));

        Assertions.assertThat(bankAccount.getBalance())
                .isEqualTo(Money.of(12.00, "UAH"));
    }
}