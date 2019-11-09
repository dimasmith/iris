package net.anatolich.iris.infra.demo;

import lombok.Data;
import net.anatolich.iris.domain.settlement.AccountingAccountId;
import net.anatolich.iris.domain.settlement.BankAccount;
import net.anatolich.iris.domain.settlement.SettlementSettings;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "iris.demo.settlement")
public class DemoSettlementSettings {
    private String bankAccountId;
    private String accountingAccountId;

    SettlementSettings toSettings() {
        return new SettlementSettings(new BankAccount.Id(bankAccountId), new AccountingAccountId(accountingAccountId));
    }
}
