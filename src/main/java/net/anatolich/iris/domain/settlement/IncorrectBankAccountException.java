package net.anatolich.iris.domain.settlement;

public class IncorrectBankAccountException extends RuntimeException {
    private final BankAccountId bankAccountId;

    public IncorrectBankAccountException(BankAccountId bankAccountId) {
        super(String.format("incorrect bank account %s", bankAccountId.getId()));
        this.bankAccountId = bankAccountId;
    }

    public BankAccountId getBankAccountId() {
        return bankAccountId;
    }
}
