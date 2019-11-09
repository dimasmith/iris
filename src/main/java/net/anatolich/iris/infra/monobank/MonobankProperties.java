package net.anatolich.iris.infra.monobank;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "iris.monobank")
@Data
public class MonobankProperties {
    private String token;

    AccessToken accessToken() {
        return new AccessToken(token);
    }
}
