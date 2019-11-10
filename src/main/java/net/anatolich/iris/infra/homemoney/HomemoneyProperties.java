package net.anatolich.iris.infra.homemoney;

import lombok.Data;

import java.util.Map;

@Data
public class HomemoneyProperties {
    private String token;
    private Map<String, String> currencies;
}
