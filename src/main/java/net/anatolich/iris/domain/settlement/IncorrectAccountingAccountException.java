package net.anatolich.iris.domain.settlement;

public class IncorrectAccountingAccountException extends RuntimeException {
    private final AccountingAccountId accountingAccountId;

    public IncorrectAccountingAccountException(AccountingAccountId accountingAccountId) {
        super(String.format("Incorrect accounting account %s", accountingAccountId.getId()));
        this.accountingAccountId = accountingAccountId;
    }

    public AccountingAccountId getAccountingAccountId() {
        return accountingAccountId;
    }
}
