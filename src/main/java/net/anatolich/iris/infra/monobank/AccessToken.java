package net.anatolich.iris.infra.monobank;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
class AccessToken {
    private final String token;
    private final String header = "X-Token";

    AccessToken(String token) {
        this.token = token;
    }
}
