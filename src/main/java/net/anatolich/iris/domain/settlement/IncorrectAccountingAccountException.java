package net.anatolich.iris.domain.settlement;

import lombok.Getter;

@Getter
public class IncorrectAccountingAccountException extends RuntimeException {
    private final AccountingAccount.Id accountId;

    public IncorrectAccountingAccountException(AccountingAccount.Id accountId) {
        super(String.format("Incorrect accounting account %s", accountId.getValue()));
        this.accountId = accountId;
    }

}
