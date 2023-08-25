package net.anatolich.iris.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.zalando.jackson.datatype.money.MoneyModule;

@Configuration
public class MoneyConfiguration {

    @Autowired
    private ObjectMapper objectMapper;

    @PostConstruct
    public void registerMoneyJsonFormatter() {
        objectMapper.registerModule(new MoneyModule());
    }
}
