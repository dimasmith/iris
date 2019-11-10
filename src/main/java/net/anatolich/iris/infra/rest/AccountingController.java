package net.anatolich.iris.infra.rest;

import lombok.Value;
import lombok.extern.java.Log;
import net.anatolich.iris.domain.settlement.AccountingAccount;
import net.anatolich.iris.domain.settlement.SettlementService;
import org.javamoney.moneta.Money;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Currency;
import java.util.List;
import java.util.stream.Collectors;

@Log
@RestController
@RequestMapping(path = "/v1/accounting")
public class AccountingController {

    private final SettlementService settlements;

    public AccountingController(SettlementService settlements) {
        this.settlements = settlements;
    }

    @GetMapping(path = "/accounts")
    public List<AccountDto> listBankAccounts() {
        List<AccountingAccount> accounts = settlements.listAvailableAccountingAccounts();
        return accounts.stream()
                .map(AccountDto::fromAccount)
                .collect(Collectors.toList());
    }

    @Value
    static class AccountDto {
        private final String accountNumber;
        private Money bookedBalance;
        private Currency currency;

        static AccountDto fromAccount(AccountingAccount account) {
            return new AccountDto(
                    account.getId().getValue(),
                    account.getBalance(),
                    Currency.getInstance(account.getCurrency().getCurrencyCode())
            );
        }

    }
}
