package net.anatolich.iris.domain.settlement;

import org.assertj.core.api.Assertions;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BalanceComparisonTest {

    @Test
    void balanceSettledWhenAmountsEqual() {
        Money bankingBalance = Money.of(1000, "UAH");
        Money accountingBalance = Money.of(1000, "UAH");

        BalanceComparison comparison = new BalanceComparison(bankingBalance, accountingBalance);

        Assertions.assertThat(comparison.isSettled())
                .as("comparison must be settled for equal amounts")
                .isTrue();
    }

    @Test
    void balanceNotSettledOnDifferentAmounts() {
        Money bankingBalance = Money.of(902, "UAH");
        Money accountingBalance = Money.of(860, "UAH");

        BalanceComparison comparison = new BalanceComparison(bankingBalance, accountingBalance);

        Assertions.assertThat(comparison.isSettled())
                .as("comparison must be not settled for different amounts")
                .isFalse();
    }
}