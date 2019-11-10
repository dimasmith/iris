package net.anatolich.iris.infra.rest;

import net.anatolich.iris.config.MoneyConfiguration;
import net.anatolich.iris.domain.settlement.AccountingAccount;
import net.anatolich.iris.domain.settlement.AccountingAccountId;
import net.anatolich.iris.domain.settlement.SettlementService;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = AccountingController.class)
@Import(MoneyConfiguration.class)
class AccountingControllerTest {

    @MockBean
    private SettlementService settlementService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void listAvailableAccounts() throws Exception {
        AccountingAccount savingsAccount = new AccountingAccount(AccountingAccountId.random(), Money.of(500, "UAH"));
        AccountingAccount depositAccount = new AccountingAccount(AccountingAccountId.random(), Money.of(1000, "USD"));
        Mockito.when(settlementService.listAvailableAccountingAccounts())
                .thenReturn(List.of(savingsAccount, depositAccount));

        mockMvc.perform(MockMvcRequestBuilders.request(HttpMethod.GET, "/v1/accounting/accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].accountNumber", equalTo(savingsAccount.getId().getId())))
                .andExpect(jsonPath("[0].bookedBalance.amount", comparesEqualTo(savingsAccount.getBalance().getNumber().doubleValue())))
                .andExpect(jsonPath("[0].bookedBalance.currency", equalTo(savingsAccount.getCurrency().getCurrencyCode())))
                .andExpect(jsonPath("[0].currency", equalTo(savingsAccount.getCurrency().getCurrencyCode())))
                .andExpect(jsonPath("[1].accountNumber", equalTo(depositAccount.getId().getId())))
                .andExpect(jsonPath("[1].bookedBalance.amount", comparesEqualTo(depositAccount.getBalance().getNumber().doubleValue())))
                .andExpect(jsonPath("[1].bookedBalance.currency", equalTo(depositAccount.getCurrency().getCurrencyCode())))
                .andExpect(jsonPath("[1].currency", equalTo(depositAccount.getCurrency().getCurrencyCode())));
    }
}