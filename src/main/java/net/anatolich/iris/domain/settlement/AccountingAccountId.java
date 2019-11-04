package net.anatolich.iris.domain.settlement;

import lombok.Value;

import java.util.UUID;

@Value
public class AccountingAccountId {
    private final String id;

    public static AccountingAccountId random() {
        return new AccountingAccountId(UUID.randomUUID().toString());
    }
}
