package net.anatolich.iris.infra.homemoney;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

class HomemoneyApiClientTest {

    private RestTemplateBuilder restTemplateBuilder = new RestTemplateBuilder();
    private RestTemplate restTemplate;
    private MockRestServiceServer mockServer;

    @BeforeEach
    void setUp() {
        restTemplate = restTemplateBuilder.build();
        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    void getBalanceListFromHomemoney() {
        final HomemoneyProperties properties = new HomemoneyProperties();
        properties.setToken("access_token");
        final HomemoneyApiClient apiClient = new HomemoneyApiClient(restTemplate, properties);

        mockServer
                .expect(requestTo("https://homemoney.ua/api/api2.asmx/BalanceList?Token=access_token"))
                .andRespond(withStatus(HttpStatus.OK)
                        .body("{}")
                        .contentType(MediaType.APPLICATION_JSON));

        final BalanceListDto balanceList = apiClient.getBalanceList();
        Assertions.assertThat(balanceList)
                .isNotNull();
    }
}