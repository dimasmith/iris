package net.anatolich.iris.domain.settlement;

import lombok.Value;
import org.javamoney.moneta.Money;

import javax.money.MonetaryAmount;
import java.util.Objects;

@Value
public class BalanceComparison {
    private final boolean settled;
    private final MonetaryAmount bankingBalance;
    private final MonetaryAmount accountingBalance;

    BalanceComparison(Money bankingBalance, Money accountingBalance) {
        this.bankingBalance = Objects.requireNonNull(bankingBalance);
        this.accountingBalance = Objects.requireNonNull(accountingBalance);
        this.settled = bankingBalance.isEqualTo(accountingBalance);
    }
}
