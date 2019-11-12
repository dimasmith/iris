package net.anatolich.iris.domain.settlement;

import org.assertj.core.api.Assertions;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.Test;

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
    void balanceOfSettledAccountsIsZero() {
        Money bankingBalance = Money.of(1000, "UAH");
        Money accountingBalance = Money.of(1000, "UAH");

        BalanceComparison comparison = new BalanceComparison(bankingBalance, accountingBalance);

        Assertions.assertThat(comparison.getBalance())
                .as("balance of settled accounts must be 0")
                .isEqualTo(Money.of(0, "UAH"));
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

    @Test
    void balanceOfUnsettledAccountsMustEqualAmountDifference() {
        Money bankingBalance = Money.of(902, "UAH");
        Money accountingBalance = Money.of(860, "UAH");

        BalanceComparison comparison = new BalanceComparison(bankingBalance, accountingBalance);

        Assertions.assertThat(comparison.getBalance())
                .as("settlement balance of accounts must be non-zero")
                .isEqualTo(Money.of(42, "UAH"));

    }
    @Test
    void balanceOfUnsettledAccountsMustBeNonNegative() {
        Money bankingBalance = Money.of(130, "UAH");
        Money accountingBalance = Money.of(330, "UAH");

        BalanceComparison comparison = new BalanceComparison(bankingBalance, accountingBalance);

        Assertions.assertThat(comparison.getBalance())
                .as("settlement balance of accounts must be non-zero")
                .isEqualTo(Money.of(200, "UAH"));

    }
}