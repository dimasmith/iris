package net.anatolich.iris.domain.settlement;

import lombok.Data;
import net.anatolich.iris.domain.settlement.BankAccount.Id;

@Data
public class SettlementProperties {

    private String bankAccountId;
    private String accountingAccountId;

    public BankAccount.Id bankingAccountId() {
        return new Id(bankAccountId);
    }

    public AccountingAccount.Id accountingAccountId() {
        return new AccountingAccount.Id(accountingAccountId);
    }

    public void setBankAccount(Id bankingId) {
        setBankAccountId(bankingId.getValue());
    }

    public void setAccountingAccount(AccountingAccount.Id accountingId) {
        setAccountingAccountId(accountingId.getValue());
    }
}
