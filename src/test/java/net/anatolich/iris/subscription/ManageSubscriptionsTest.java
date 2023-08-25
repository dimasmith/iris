package net.anatolich.iris.subscription;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.anatolich.iris.subscription.infra.rest.SubscriptionDto;
import net.anatolich.iris.subscription.infra.rest.SubscriptionDto.ServiceDto;
import org.javamoney.moneta.Money;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJson;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJson
@DisplayName("manage subscriptions")
@ActiveProfiles(profiles = "it")
class ManageSubscriptionsTest {

    @Autowired
    private SubscriptionRestClient restClient;

    @Test
    @DisplayName("subscribe to service and store subscription")
    void subscribeToService() throws Exception {
        final ServiceDto service = ServiceDto.builder()
            .name("Dropbox")
            .url("https://dropbox.com")
            .description("file synchronization service")
            .build();
        final Money rate = Money.of(BigDecimal.valueOf(11.99), "USD");
        final SubscriptionDto command = SubscriptionDto.builder()
            .service(service)
            .rate(rate)
            .build();
        restClient.subscribe(command)
            .andExpect(status().isCreated());

        restClient.listSubscriptions()
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].service.name", equalTo(service.getName())))
            .andExpect(jsonPath("$[0].service.url", equalTo(service.getUrl())))
            .andExpect(jsonPath("$[0].service.description", equalTo(service.getDescription())))
            .andExpect(jsonPath("$[0].rate.currency", equalTo(rate.getCurrency().getCurrencyCode())))
            .andExpect(jsonPath("$[0].rate.amount", closeTo(
                rate.getNumberStripped().doubleValue(), 0.001)));
    }

    @TestConfiguration
    static class RestClientConfiguration {

        @Bean
        public SubscriptionRestClient subscriptionRestClient(MockMvc mockMvc, ObjectMapper json) {
            return new SubscriptionRestClient(mockMvc, json);
        }
    }
}
