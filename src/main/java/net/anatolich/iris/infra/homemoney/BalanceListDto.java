package net.anatolich.iris.infra.homemoney;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Value;
import net.anatolich.iris.domain.settlement.AccountingAccount;
import net.anatolich.iris.domain.settlement.AccountingAccountId;
import net.anatolich.iris.domain.settlement.IncorrectAccountingAccountException;
import org.javamoney.moneta.Money;

import javax.money.CurrencyUnit;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
class BalanceListDto {
    @JsonProperty("Error")
    private Error error;
    @JsonProperty("ListGroupInfo")
    private List<GroupInfo> groupInfo;

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
    }

    @Data
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
