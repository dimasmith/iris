package net.anatolich.iris.domain.settlement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SettlementSettings {
    private BankAccountId bankAccountId;
    private AccountingAccountId accountingAccountId;
}