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
    private final Money balance;

    BalanceComparison(Money bankingBalance, Money accountingBalance) {
        this.bankingBalance = Objects.requireNonNull(bankingBalance);
        this.accountingBalance = Objects.requireNonNull(accountingBalance);
        this.settled = bankingBalance.isEqualTo(accountingBalance);
        this.balance = calculateBalance(bankingBalance, accountingBalance);
    }

    private Money calculateBalance(Money bankingBalance, Money accountingBalance) {
        return bankingBalance.subtract(accountingBalance).abs();
    }
}
