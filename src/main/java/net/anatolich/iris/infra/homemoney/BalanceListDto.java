package net.anatolich.iris.infra.homemoney;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import net.anatolich.iris.domain.settlement.AccountingAccount;
import net.anatolich.iris.domain.settlement.AccountingAccountId;
import net.anatolich.iris.domain.settlement.IncorrectAccountingAccountException;
import org.javamoney.moneta.Money;

import javax.money.CurrencyUnit;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
class BalanceListDto {
    @JsonProperty("Error")
    private Error error;
    @JsonProperty("ListGroupInfo")
    private List<GroupInfo> groupInfo;

    List<AccountingAccount> toAccounts(CurrencyResolver currencyResolver) {
        return groupInfo.stream()
                .flatMap(GroupInfo::accounts)
                .flatMap(accountInfo -> accountInfo.toAccounts(currencyResolver).stream())
                .collect(Collectors.toList());
    }

    @Value
    static class AccountIdentifier {
        private final String accountId;
        private final String currencyId;

        AccountIdentifier(AccountingAccountId id) {
            final String idValue = id.getId();
            if (idValue.indexOf('/') != idValue.lastIndexOf('/')) {
                throw new IllegalArgumentException("incorrect id format. an expected format is <accountId>/<currencyId>");
            }
            final String[] idComponents = idValue.split("/");
            if (idComponents.length != 2) {
                throw new IllegalArgumentException("incorrect id format. an expected format is <accountId>/<currencyId>");
            }
            this.accountId = idComponents[0];
            this.currencyId = idComponents[1];
        }

        AccountIdentifier(AccountInfo accountInfo, CurrencyInfo currencyInfo) {
            this.accountId = accountInfo.getId();
            this.currencyId = currencyInfo.getId();
        }

        private AccountingAccountId toAccountingId() {
            return new AccountingAccountId(String.format("%s/%s", accountId, currencyId));
        }

        boolean matches(AccountInfo accountInfo) {
            return accountInfo.getId().equals(getAccountId());
        }

        boolean matches(CurrencyInfo currencyInfo) {
            return currencyInfo.getId().equals(getCurrencyId());
        }
    }

    AccountingAccount getAccount(AccountingAccountId accountId, CurrencyResolver currencyResolver) {
        final AccountIdentifier id = new AccountIdentifier(accountId);

        final AccountInfo foundAccountInfo = findAccountInfo(id)
                .orElseThrow(() -> new IncorrectAccountingAccountException(accountId));

        final CurrencyInfo foundCurrencyInfo = foundAccountInfo.findCurrencyInfo(id)
                .orElseThrow(() -> new IncorrectAccountingAccountException(accountId));

        final Money balance = Money.of(foundCurrencyInfo.balance, foundCurrencyInfo.getCurrency(currencyResolver));
        return new AccountingAccount(accountId, balance);
    }

    private Optional<AccountInfo> findAccountInfo(AccountIdentifier id) {
        return groupInfo.stream()
                .flatMap(GroupInfo::accounts)
                .filter(id::matches)
                .findFirst();
    }

    @Data
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    static class GroupInfo {
        private String id;
        private String name;
        private boolean hasAccounts;
        private boolean hasShowAccounts;
        private int order;
        @JsonProperty("ListAccountInfo")
        private List<AccountInfo> accountInfo;

        Stream<AccountInfo> accounts() {
            return accountInfo.stream();
        }
    }

    @Data
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    static class AccountInfo {
        private String id;
        private String name;
        private boolean isDefault;
        private boolean includeInBalance;
        private boolean hasOpenCurrencies;
        private boolean isShowInGroup;
        @JsonProperty("ListCurrencyInfo")
        private List<CurrencyInfo> currencyInfo;

        private Optional<CurrencyInfo> findCurrencyInfo(AccountIdentifier id) {
            return getCurrencyInfo().stream()
                    .filter(id::matches)
                    .findFirst();
        }

        private List<AccountingAccount> toAccounts(CurrencyResolver resolver) {
            return currencyInfo.stream()
                    .map(info -> toAccount(info, resolver))
                    .collect(Collectors.toList());

        }

        private AccountingAccount toAccount(CurrencyInfo currencyInfo, CurrencyResolver resolver) {
            final AccountIdentifier accountIdentifier = new AccountIdentifier(this, currencyInfo);
            return new AccountingAccount(
                    accountIdentifier.toAccountingId(),
                    Money.of(currencyInfo.getBalance(), currencyInfo.getCurrency(resolver))
            );
        }

    }

    @Data
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    static class CurrencyInfo {
        private String id;
        private String shortname;
        private BigDecimal rate;
        private BigDecimal balance;
        private boolean display;

        private CurrencyUnit getCurrency(CurrencyResolver currencyResolver) {
            return currencyResolver.resolveCurrency(id);
        }
    }

    @Data
    static class Error {
        private int code;
        private String message;
    }
}
