package net.anatolich.iris.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.zalando.jackson.datatype.money.MoneyModule;

import javax.annotation.PostConstruct;

@Configuration
public class MoneyConfiguration {

    @Autowired
    private ObjectMapper objectMapper;

    @PostConstruct
    public void registerMoneyJsonFormatter() {
        objectMapper.registerModule(new MoneyModule());
    }
}
