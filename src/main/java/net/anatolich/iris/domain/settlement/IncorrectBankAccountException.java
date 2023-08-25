package net.anatolich.iris.domain.settlement;

import lombok.Getter;

@Getter
public class IncorrectBankAccountException extends RuntimeException {
    private final BankAccount.Id bankAccountId;

    public IncorrectBankAccountException(BankAccount.Id bankAccountId) {
        super(String.format("incorrect bank account %s", bankAccountId.getValue()));
        this.bankAccountId = bankAccountId;
    }

}
