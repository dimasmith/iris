package net.anatolich.iris.infra.monobank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder @NoArgsConstructor @AllArgsConstructor
public class MonobankProperties {
    private String token;

    AccessToken accessToken() {
        return new AccessToken(token);
    }
}
