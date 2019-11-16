package net.anatolich.iris.domain.settlement;

import java.time.ZonedDateTime;
import lombok.Value;
import org.javamoney.moneta.Money;

import javax.money.MonetaryAmount;
import java.util.Objects;

@Value
public class SettlementCheck {
    private final boolean settled;
    private final MonetaryAmount bankingBalance;
    private final MonetaryAmount accountingBalance;
    private final Money balance;
    private final ZonedDateTime checkTime;

    SettlementCheck(Money bankingBalance, Money accountingBalance) {
        this.bankingBalance = Objects.requireNonNull(bankingBalance);
        this.accountingBalance = Objects.requireNonNull(accountingBalance);
        this.settled = bankingBalance.isEqualTo(accountingBalance);
        this.balance = calculateBalance(bankingBalance, accountingBalance);
        this.checkTime = ZonedDateTime.now();
    }

    private Money calculateBalance(Money bankingBalance, Money accountingBalance) {
        return bankingBalance.subtract(accountingBalance).abs();
    }
}
