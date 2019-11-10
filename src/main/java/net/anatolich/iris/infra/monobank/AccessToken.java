package net.anatolich.iris.infra.monobank;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
class AccessToken {
    static final String HEADER = "X-Token";
    private final String token;

    AccessToken(String token) {
        this.token = token;
    }
}
