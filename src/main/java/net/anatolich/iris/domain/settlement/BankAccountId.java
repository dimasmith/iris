package net.anatolich.iris.domain.settlement;

import lombok.Value;

import java.util.UUID;

@Value
public class BankAccountId {
    private final String id;

    public static BankAccountId random() {
        return new BankAccountId(UUID.randomUUID().toString());
    }
}
